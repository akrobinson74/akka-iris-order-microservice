package com.olx.iris.model

case class Customer(
  address: Address,
  businessName: Option[String],
  emailAddress: String,
  firstName: String,
  language: String,
  lastName: String,
  `type`: CustomerType,
  userId: String,
  vatNumber: Option[String]
)