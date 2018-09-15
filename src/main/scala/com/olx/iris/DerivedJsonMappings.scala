package com.olx.iris

import com.olx.iris.model._
import spray.json.{DefaultJsonProtocol, DerivedFormats, JsString, JsValue, RootJsonFormat}

trait DerivedJsonMappings extends DefaultJsonProtocol with DerivedFormats {
  implicit val invoiceTypeFormat: RootJsonFormat[InvoiceType] = jsonFormat[InvoiceType]
  implicit val productCategoryFormat: RootJsonFormat[ProductCategory] = jsonFormat[ProductCategory]
  implicit val transactionIdFormat: RootJsonFormat[TransactionId] = jsonFormat[TransactionId]

  implicit val customerTypeFormat = new RootJsonFormat[CustomerType] {
    override def write(obj: CustomerType): JsValue = new JsString(value = obj match {
      case CustomerType.BUSINESS => "BUSINESS"
      case CustomerType.RESIDENTIAL => "RESIDENTIAL"
    })

    override def read(json: JsValue): CustomerType = CustomerType(value = json)
  }

  implicit val productTypeFormat = new RootJsonFormat[ProductType] {
    override def write(obj: ProductType): JsValue = new JsString(value = obj match {
      case ProductType.AD_UPGRADE => "AD_UPGRADE"
      case ProductType.EFFORT => "EFFORT"
      case ProductType.LIMIT => "LIMIT"
      case ProductType.REWARD => "REWARD"
      case ProductType.TOPUP => "TOPUP"
    })

    override def read(json: JsValue): ProductType = ProductType(value = json)
  }

  implicit val revenueClassFormat = new RootJsonFormat[RevenueClass] {
    override def write(obj: RevenueClass): JsValue = new JsString(value = obj match {
      case RevenueClass.DAILY => "DAILY"
      case RevenueClass.MONTHLY => "MONTH"
      case RevenueClass.USAGE => "USAGE"
    })

    override def read(json: JsValue): RevenueClass = RevenueClass(value = json)
  }


}
