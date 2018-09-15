package com.olx.iris.model

import akka.actor.Props
import akka.pattern.pipe
import akka.persistence.{ PersistentActor, SnapshotOffer }

import scala.concurrent.{ Future, Promise }

class TransactionEntity extends PersistentActor {

  import TransactionEntity._
  import context._

  private var state = TransactionState()

  override def persistenceId: String = "transaction"

  override def receiveCommand: Receive = {
    case AddOrder(transaction) =>
      handleEvent(OrderAdded(TransactionId(), transaction)) pipeTo sender()
      ()
    case GetOrder(id) =>
      sender() ! state(id)
    case UpdateOrder(id, transaction) =>
      state(id) match {
        case response @ Left(_) => sender() ! response
        case Right(_) =>
          handleEvent(OrderUpdated(id, transaction)) pipeTo sender()
          ()
      }
  }

  override def receiveRecover: Receive = {
    case event: TransactionEvent                      => state += event
    case SnapshotOffer(_, snapshot: TransactionState) => state = snapshot
  }

  private def handleEvent[E <: TransactionEvent](e: => E): Future[E] = {
    val p = Promise[E]
    persist(e) { event =>
      p.success(event)
      state += event
      system.eventStream.publish(event)
      if (lastSequenceNr != 0 && lastSequenceNr % 1000 == 0) saveSnapshot(state)
    }
    p.future
  }
}

object TransactionEntity {
  def props = Props(new TransactionEntity)

  sealed trait TransactionCommand
  final case class AddOrder(order: Order) extends TransactionCommand
  final case class GetOrder(id: TransactionId) extends TransactionCommand
  final case class UpdateOrder(id: TransactionId, order: Order) extends TransactionCommand

  sealed trait TransactionEvent {
    val id: TransactionId
    val order: Order
  }
  final case class OrderAdded(id: TransactionId, order: Order) extends TransactionEvent
  final case class OrderUpdated(id: TransactionId, order: Order) extends TransactionEvent
  final case class TransactionNotFound(id: TransactionId) extends RuntimeException(s"No Transaction found with id: $id")

  type MaybeOrder[+A] = Either[TransactionNotFound, A]

  final case class TransactionState(history: Map[TransactionId, Order]) {
    def apply(id: TransactionId): MaybeOrder[Order] = history.get(id).toRight(TransactionNotFound(id))
    def +(event: TransactionEvent): TransactionState = TransactionState(history.updated(event.id, event.order))
  }
  object TransactionState {
    def apply(): TransactionState = TransactionState(Map.empty)
  }
}
