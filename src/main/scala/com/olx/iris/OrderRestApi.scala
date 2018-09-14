package com.olx.iris

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import akka.pattern.ask
import com.olx.iris.akkaHttp.{MyAkkaConfiguration, NoCirceRestApi}
import com.olx.iris.model.{Transaction, TransactionEntity, TransactionId}

import scala.concurrent.Future

trait OrderService extends MyAkkaConfiguration {

  import com.olx.iris.model.TransactionEntity._

  private val transactionEntity = actorRefFactory.actorOf(TransactionEntity.props)

  def addTransaction(transaction: Transaction): Future[TransactionAdded] =
    (transactionEntity ? AddTransaction(transaction)).mapTo[TransactionAdded]

  def getTransaction(id: TransactionId): Future[MaybeTransaction[Transaction]] =
    (transactionEntity ? GetTransaction(id)).mapTo[MaybeTransaction[Transaction]]

  def updateTransaction(id: TransactionId, transaction: Transaction): Future[MaybeTransaction[TransactionUpdated]] =
    (transactionEntity ? UpdateTransaction(id, transaction)).mapTo[MaybeTransaction[TransactionUpdated]]
}

trait OrderRestApi extends NoCirceRestApi with OrderService {
  override def route: Route =
    pathPrefix("/orders") {
      (pathEndOrSingleSlash & post) {
        entity(as[Transaction]) { transaction =>
          onSuccess(addTransaction(transaction)) { added => complete((StatusCodes.Created, added))
          }
        }
      } ~
        pathPrefix(JavaUUID.map(TransactionId(_))) { id =>
          pathEndOrSingleSlash {
            get {
              onSuccess(getTransaction(id)) {
                case Right(transaction) => complete((StatusCodes.OK, transaction))
                case Left(errorMsg)     => complete((StatusCodes.NotFound, errorMsg))
              }
            } ~
              put {
                entity(as[Transaction]) { transaction =>
                  onSuccess(updateTransaction(id, transaction)) {
                    case Right(updated) => complete((StatusCodes.OK, updated))
                    case Left(errorMsg) => complete((StatusCodes.NotFound, errorMsg))
                  }
                }
              }
          }
        }
    }
}
