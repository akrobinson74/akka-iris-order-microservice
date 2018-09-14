package com.olx.iris.model
import java.time.ZonedDateTime

case class Product(
  currency: String = "CRD",
  description: String,
  discountAmount: Option[BigDecimal],
  expiryDateTime: Option[ZonedDateTime],
  grossPrice: BigDecimal = BigDecimal(0.00),
  netPrice: Option[BigDecimal],
  productType: ProductType,
  unitPrice: BigDecimal = BigDecimal(1.00),
  units: BigInt,
  vatAmount: Option[BigDecimal],
  vatPercentage: BigDecimal = BigDecimal(0.00)) {
}
