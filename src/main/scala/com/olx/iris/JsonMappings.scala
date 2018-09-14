package com.olx.iris

import java.time.ZonedDateTime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DeserializationException, JsString, JsValue, RootJsonFormat}

trait JsonMappings extends SprayJsonSupport with DerivedJsonMappings {

  import com.olx.iris.model._
  import com.olx.iris.model.TransactionEntity.{ TransactionAdded, TransactionUpdated }

  implicit object ZonedDateTimeJsonFormat extends RootJsonFormat[ZonedDateTime] {
    override def write(obj: ZonedDateTime) = JsString(obj.toString)

    override def read(json: JsValue): ZonedDateTime = json match {
      case JsString(s) => ZonedDateTime.parse(s)
      case _           => throw new DeserializationException("Error info you want here ...")
    }
  }

  implicit val addressFormat = jsonFormat9(Address)
  implicit val amountFormat = jsonFormat2(MonetaryAmount)
  implicit val vatDataFormat = jsonFormat2(VatData)
  implicit val customerFormat = jsonFormat9(Customer)
  implicit val paymentReferenceFormat = jsonFormat2(PaymentReference)
  implicit val productFormat = jsonFormat13(Product)
  implicit val transactionFormat = jsonFormat4(Transaction)
  implicit val transactionAddedFormat = jsonFormat2(TransactionAdded)
  implicit val transactionUpdatedFormat = jsonFormat2(TransactionUpdated)
}
