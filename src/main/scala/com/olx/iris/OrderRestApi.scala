package com.olx.iris

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import akka.pattern.ask
import com.olx.iris.akkaHttp.{ MyAkkaConfiguration, SprayRestApi }
import com.olx.iris.model.{ Order, TransactionEntity, TransactionId }
import com.olx.iris.model.TransactionEntity.{
  AddOrder,
  GetOrder,
  MaybeOrder,
  OrderAdded,
  OrderUpdated,
  UpdateOrder
}

import scala.concurrent.Future

trait OrderService extends MyAkkaConfiguration {

  private val transactionEntity = actorRefFactory.actorOf(TransactionEntity.props)

  def addOrder(order: Order): Future[OrderAdded] =
    (transactionEntity ? AddOrder(order)).mapTo[OrderAdded]

  def getOrder(id: TransactionId): Future[MaybeOrder[Order]] =
    (transactionEntity ? GetOrder(id)).mapTo[MaybeOrder[Order]]

  def updateOrder(id: TransactionId, transaction: Order): Future[MaybeOrder[OrderUpdated]] =
    (transactionEntity ? UpdateOrder(id, transaction)).mapTo[MaybeOrder[OrderUpdated]]
}

trait OrderRestApi extends SprayRestApi with OrderService {
  override def route: Route =
    pathPrefix("orders") {
      (pathEndOrSingleSlash & post) {
        entity(as[Order]) { transaction =>
          onSuccess(addOrder(transaction)) { added => complete((StatusCodes.Created, added))
          }
        }
      } ~
        pathPrefix(JavaUUID.map(TransactionId(_))) { id =>
          pathEndOrSingleSlash {
            get {
              onSuccess(getOrder(id)) {
                case Right(transaction) => complete((StatusCodes.OK, transaction))
                case Left(errorMsg)     => complete((StatusCodes.NotFound, errorMsg))
              }
            } ~
              put {
                entity(as[Order]) { transaction =>
                  onSuccess(updateOrder(id, transaction)) {
                    case Right(updated) => complete((StatusCodes.OK, updated))
                    case Left(errorMsg) => complete((StatusCodes.NotFound, errorMsg))
                  }
                }
              }
          }
        }
    }
}
