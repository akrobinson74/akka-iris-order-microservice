package com.olx.iris.model

case class Customer(
  address: Address,
  businessName: Option[String],
  customerType: CustomerType,
  emailAddress: String,
  firstName: String,
  language: String,
  lastName: String,
  userId: String,
  vatNumber: Option[String]
)