package com.olx.iris.model
import spray.json.{ JsString, JsValue }

sealed trait Status

object Status {
  def apply(value: JsValue): Status = value match {
    case JsString("ACTIVE")   => ACTIVE
    case JsString("INACTIVE") => INACTIVE
    case JsString("PENDING")  => PENDING
    case _                    =>
      throw new IllegalArgumentException(s"Only JsString values are valid for Order.status not: $value")
  }

  final case object ACTIVE extends Status
  final case object INACTIVE extends Status
  final case object PENDING extends Status
}
