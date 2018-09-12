package com.olx.iris.model

import akka.actor.Props
import akka.persistence.PersistentActor

class TransactionEntity extends PersistentActor {
  override def persistenceId: String = "transaction"

  override def receiveCommand: Receive = ???

  override def receiveRecover: Receive = ???
}

object TransactionEntity {
  def props = Props(new TransactionEntity)

  sealed trait TransactionCommand

  final case class GetTransaction(transactionReference: )
}
