package com.olx.iris.model

final case class Order(
  customer: Customer,
  orderId: String,
  paymentReference: PaymentReference,
  products: List[Product],
  source: String,
  status: Status
)
