package com.olx.iris

import com.olx.iris.model.{CustomerType, InvoiceType, ProductCategory, ProductType, RevenueClass, TransactionId}
import spray.json.{DefaultJsonProtocol, DerivedFormats, RootJsonFormat}

trait DerivedJsonMappings extends DefaultJsonProtocol with DerivedFormats {
  implicit val customerTypeFormat: RootJsonFormat[CustomerType] = jsonFormat[CustomerType]
  implicit val invoiceTypeFormat: RootJsonFormat[InvoiceType] = jsonFormat[InvoiceType]
  implicit val productCategoryFormat: RootJsonFormat[ProductCategory] = jsonFormat[ProductCategory]
  implicit val productTypeFormat: RootJsonFormat[ProductType] = jsonFormat[ProductType]
  implicit val revenueClassFormat: RootJsonFormat[RevenueClass] = jsonFormat[RevenueClass]
  implicit val transactionIdFormat: RootJsonFormat[TransactionId] = jsonFormat[TransactionId]
}
