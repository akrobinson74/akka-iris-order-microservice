package com.olx.iris.model

import spray.json.{JsString, JsValue}

sealed trait RevenueClass

object RevenueClass {
  def apply(value: JsValue): RevenueClass = value match {
    case JsString("DAILY") => DAILY
    case JsString("MONTHLY") => MONTHLY
    case JsString("USAGE") => USAGE
    case _ => throw new IllegalArgumentException(s"Only JsString values are valid for RevenueClass not: $value")
  }

  final case object DAILY extends RevenueClass
  final case object MONTHLY extends RevenueClass
  final case object USAGE extends RevenueClass
}
