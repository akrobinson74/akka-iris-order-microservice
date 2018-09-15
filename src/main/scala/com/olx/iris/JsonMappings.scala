package com.olx.iris

import java.time.ZonedDateTime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, DerivedFormats, DeserializationException, JsString, JsValue, RootJsonFormat}

trait JsonMappings extends SprayJsonSupport with DefaultJsonProtocol with DerivedFormats {

  import com.olx.iris.model._
  import com.olx.iris.model.TransactionEntity._

  implicit object ZonedDateTimeJsonFormat extends RootJsonFormat[ZonedDateTime] {
    override def write(obj: ZonedDateTime) = JsString(obj.toString)

    override def read(json: JsValue): ZonedDateTime = json match {
      case JsString(s) => ZonedDateTime.parse(s)
      case _           => throw new DeserializationException("Error info you want here ...")
    }
  }

//  implicit val orderRootFormat: RootJsonFormat[Order] = jsonFormat[Order]
  implicit val invoiceTypeFormat: RootJsonFormat[InvoiceType] = jsonFormat[InvoiceType]
  implicit val productCategoryFormat: RootJsonFormat[ProductCategory] = jsonFormat[ProductCategory]
  implicit val transactionIdFormat: RootJsonFormat[TransactionId] = jsonFormat[TransactionId]
//  implicit val transactionUpdatedFormat: RootJsonFormat[TransactionUpdated] =
//    jsonFormat[TransactionUpdated]

  implicit val customerTypeFormat = new RootJsonFormat[CustomerType] {
    override def write(obj: CustomerType): JsValue =
      new JsString(value = obj match {
        case CustomerType.BUSINESS    => "BUSINESS"
        case CustomerType.RESIDENTIAL => "RESIDENTIAL"
      })

    override def read(json: JsValue): CustomerType = CustomerType(value = json)
  }

  implicit val productTypeFormat = new RootJsonFormat[ProductType] {
    override def write(obj: ProductType): JsValue =
      new JsString(value = obj match {
        case ProductType.AD_UPGRADE => "AD_UPGRADE"
        case ProductType.EFFORT     => "EFFORT"
        case ProductType.LIMIT      => "LIMIT"
        case ProductType.REWARD     => "REWARD"
        case ProductType.TOPUP      => "TOPUP"
      })

    override def read(json: JsValue): ProductType = ProductType(value = json)
  }

  implicit val revenueClassFormat = new RootJsonFormat[RevenueClass] {
    override def write(obj: RevenueClass): JsValue =
      new JsString(value = obj match {
        case RevenueClass.DAILY   => "DAILY"
        case RevenueClass.MONTHLY => "MONTH"
        case RevenueClass.USAGE   => "USAGE"
      })

    override def read(json: JsValue): RevenueClass = RevenueClass(value = json)
  }

  implicit val orderStatusFormat = new RootJsonFormat[Status] {
    override def write(obj: Status): JsValue =
      new JsString(value = obj match {
        case Status.ACTIVE   => "ACTIVE"
        case Status.INACTIVE => "INACTIVE"
        case Status.PENDING  => "PENDING"
      })
    override def read(json: JsValue): Status = Status(value = json)
  }

  implicit val addressFormat = jsonFormat9(Address)
  implicit val amountFormat = jsonFormat2(MonetaryAmount)
  implicit val vatDataFormat = jsonFormat2(VatData)
  implicit val customerFormat = jsonFormat9(Customer)
  implicit val paymentReferenceFormat = jsonFormat2(PaymentReference)
  implicit val productFormat = jsonFormat13(Product)
  implicit val orderFormat = jsonFormat6(Order)

  implicit val transactionAddedFormat = jsonFormat2(OrderAdded)
  implicit val transactionUpdatedFormat = jsonFormat2(OrderUpdated)
}
