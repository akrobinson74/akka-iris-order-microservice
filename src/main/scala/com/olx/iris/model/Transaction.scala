package com.olx.iris.model

final case class Transaction(
  customer: Customer,
  paymentReference: PaymentReference,
  products: List[Product],
  transactionReference: String
)
