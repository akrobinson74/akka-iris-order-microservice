package com.olx.iris.model

import spray.json.{JsString, JsValue}

sealed trait CustomerType

object CustomerType {
  def apply(value: JsValue): CustomerType = value match {
    case JsString("BUSINESS") => BUSINESS
    case JsString("RESIDENTIAL") => RESIDENTIAL
    case _ => throw new IllegalArgumentException(s"Only JsString values are valid for CustomerType not: $value")
  }

  case object BUSINESS extends CustomerType

  case object RESIDENTIAL extends CustomerType

}
