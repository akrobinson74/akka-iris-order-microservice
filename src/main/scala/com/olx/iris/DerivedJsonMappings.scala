package com.olx.iris

import com.olx.iris.model.{CustomerType, InvoiceType, ProductCategory, ProductType, RevenueClass, TransactionId}
import spray.json.{DefaultJsonProtocol, DerivedFormats, JsString, JsValue, RootJsonFormat}

trait DerivedJsonMappings extends DefaultJsonProtocol with DerivedFormats {
  implicit val customerTypeFormat = new RootJsonFormat[CustomerType] {
    override def write(obj: CustomerType): JsValue = new JsString(value = obj match {
      case CustomerType.BUSINESS => "BUSINESS"
      case CustomerType.RESIDENTIAL => "RESIDENTIAL"
    })
    override def read(json: JsValue): CustomerType = ???
  }
  implicit val invoiceTypeFormat: RootJsonFormat[InvoiceType] = jsonFormat[InvoiceType]
  implicit val productCategoryFormat: RootJsonFormat[ProductCategory] = jsonFormat[ProductCategory]
//  implicit val productTypeFormat: RootJsonFormat[ProductType] = jsonFormat[ProductType]
  implicit val productTypeFormat = new RootJsonFormat[ProductType] {
    override def write(obj: ProductType): JsValue = new JsString(value = obj match {
      case ProductType.AD_UPGRADE => "AD_UPGRADE"
      case ProductType.EFFORT => "EFFORT"
      case ProductType.LIMIT => "LIMIT"
      case ProductType.REWARD => "REWARD"
      case ProductType.TOPUP => "TOPUP"
    })
    override def read(json: JsValue): ProductType = ???
  }
//  implicit val revenueClassFormat: RootJsonFormat[RevenueClass] = jsonFormat[RevenueClass]
  implicit val revenueClassFormat = new RootJsonFormat[RevenueClass] {
    override def write(obj: RevenueClass): JsValue = new JsString(value = obj match {
      case RevenueClass.DAILY => "DAILY"
      case RevenueClass.MONTHLY => "MONTH"
      case RevenueClass.USAGE => "USAGE"
    } )
    override def read(json: JsValue): RevenueClass = ???
  }
  implicit val transactionIdFormat: RootJsonFormat[TransactionId] = jsonFormat[TransactionId]
}
