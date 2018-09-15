package com.olx.iris.model

import spray.json.{JsString, JsValue}

sealed trait ProductType {
  val fiqasTransactionType: String
}

object ProductType {
  case object AD_UPGRADE extends ProductType {
    override val fiqasTransactionType: String = "CashPurchase"
  }
  case object EFFORT extends ProductType {
    override val fiqasTransactionType: String = "Reward"
  }
  case object LIMIT extends ProductType {
    override val fiqasTransactionType: String = "CashPurchase"
  }
  case object REWARD extends ProductType {
    override val fiqasTransactionType: String = "Reward"
  }
  case object TOPUP extends ProductType {
    override val fiqasTransactionType: String = "WalletTopUp"
  }

  def apply(value: JsValue): ProductType = value match {
    case JsString("AD_UPGRADE") => AD_UPGRADE
    case JsString("EFFORT") => EFFORT
    case JsString("LIMIT") => LIMIT
    case JsString("REWARD") => REWARD
    case JsString("TOPUP") => TOPUP
    case _ => throw new IllegalArgumentException(s"Only JsString values are valid for ProductType not: $value")
  }
}
