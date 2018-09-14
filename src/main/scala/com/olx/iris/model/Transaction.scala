package com.olx.iris.model

final case class Transaction(
  customer: Customer,
  paymentReference: PaymentReference,
  items: List[Product],
  transactionReference: String
)
