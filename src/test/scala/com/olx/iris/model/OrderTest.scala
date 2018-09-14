package com.olx.iris.model

import java.time.{Instant, ZonedDateTime, ZoneId}
import java.time.temporal.ChronoUnit

import com.olx.iris.JsonMappings
import org.scalatest.FunSuite
import spray.json._

class OrderTest extends FunSuite with JsonMappings {

  test("Kotlin Iris payload can be deserialized") {
    val nowInstant = Instant.now()
    val now = ZonedDateTime.ofInstant(nowInstant, ZoneId.of("UTC"))
    val expiry = ZonedDateTime.ofInstant(
      nowInstant.plus(182L, ChronoUnit.DAYS),
      ZoneId.of("UTC")
    )
    val transactionJson = s"""{
|  "customer": {
|    "emailAddress": "adam.robinson@olx.com",
|    "customerType": "BUSINESS",
|    "lastName": "Robinson",
|    "firstName": "Adam",
|    "language": "en-US",
|    "businessName": "I'm Awesome",
|    "address": {
|      "houseNumber": "7001",
|      "city": "New Orleans",
|      "state": "LA",
|      "zipCode": "70126",
|      "country": "USA",
|      "addressLines": ["addressLine1", "addressLine2"],
|      "region": "Deep South",
|      "street": "Neptune Ct"
|    },
|    "userId": "1"
|  },
|  "orderId": "1",
|  "paymentReference": {
|    "amount": {
|      "currency": "USD",
|      "amount": 100.0
|    },
|    "executionTime": "${now}"
|  },
|  "products": [{
|    "name": "A really cool product",
|    "expirationTime": "${expiry}",
|    "orderItemId": "1",
|    "description": "An Awesome Product",
|    "revenueClass": "USAGE",
|    "units": 100,
|    "category": {
|      "level1": "Stuff"
|    },
|    "unitPrice": 1.0,
|    "type": "AD_UPGRADE",
|    "skuId": "",
|    "packageId": "0",
|    "activationTime": "${now}",
|    "grossPrice": 0.0
|  }]
|}""".stripMargin

    val tx = new Order(
      customer = new Customer(
        address = new Address(
          addressLines = List[String]("addressLine1", "addressLine2"),
          city = "New Orleans",
          country = "USA",
          houseNumber = "7001",
          region = Option[String]("Deep South"),
          state = Option[String]("LA"),
          stateCode = Option.empty,
          street = "Neptune Ct",
          zipCode = "70126"
        ),
        businessName = Option[String]("I'm Awesome"),
        customerType = CustomerType.BUSINESS,
        emailAddress = "adam.robinson@olx.com",
        firstName = "Adam",
        language = "en-US",
        lastName = "Robinson",
        userId = "1",
        vatNumber = Option.empty
      ),
      paymentReference = new PaymentReference(
        amount = new MonetaryAmount(
          amount = BigDecimal("100.0"),
          currency = "USD"
        ),
        executionTime = now
      ),
      products = List(
        new Product(
          activationTime = now,
          category = new ProductCategory(
            level1 = "Stuff",
            level2 = Option.empty,
            level3 = Option.empty
          ),
          description = "An Awesome Product",
          expirationTime = expiry,
          name = "A really cool product",
          orderItemId = "1",
          packageId = "0",
          revenueClass = RevenueClass.USAGE,
          skuId = "",
          `type` = ProductType.AD_UPGRADE,
          unitPrice = BigDecimal("1.0"),
          units = BigInt("100")
        )
      ),
      orderId = "1"
    ).toJson

    println(tx.prettyPrint)
    assert(tx.prettyPrint == transactionJson)
  }
}
