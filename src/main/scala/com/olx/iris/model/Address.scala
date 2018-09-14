package com.olx.iris.model

case class Address (
  addressLines: List[String] = List(),
  city: String,
  country: String,
  houseNumber: String,
  region: Option[String] = Option.empty,
  state: Option[String] = Option.empty,
  stateCode: Option[String] = Option.empty,
  street: String,
  zipCode: String
)
