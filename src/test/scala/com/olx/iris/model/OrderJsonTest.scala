package com.olx.iris.model

import java.time.{Instant, ZonedDateTime, ZoneId}
import java.time.temporal.ChronoUnit

import com.olx.iris.JsonMappings
import org.scalatest.FunSuite
import spray.json._

class OrderJsonTest extends FunSuite with JsonMappings {

  test("Kotlin Iris payload can be deserialized") {
    val nowInstant = Instant.now()
    val now = ZonedDateTime.ofInstant(nowInstant, ZoneId.of("UTC"))
    val expiry = ZonedDateTime.ofInstant(
      nowInstant.plus(182L, ChronoUnit.DAYS),
      ZoneId.of("UTC")
    )
    val transactionJson = s"""{
|  "source": "ONLINE",
|  "orderId": "1",
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
|  }],
|  "paymentReference": {
|    "amount": {
|      "currency": "USD",
|      "value": 100.0
|    },
|    "executionTime": "${now}"
|  },
|  "status": "ACTIVE",
|  "customer": {
|    "emailAddress": "adam.robinson@olx.com",
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
|    "userId": "1",
|    "type": "BUSINESS"
|  }
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
        emailAddress = "adam.robinson@olx.com",
        firstName = "Adam",
        language = "en-US",
        lastName = "Robinson",
        `type` = CustomerType.BUSINESS,
        userId = "1",
        vatNumber = Option.empty
      ),
      orderId = "1",
      paymentReference = new PaymentReference(
        amount = new MonetaryAmount(
          currency = "USD",
          value = BigDecimal("100.0")
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
      source = "ONLINE",
      status = Status.ACTIVE
    ).toJson

    println(tx.prettyPrint)
    assert(tx.prettyPrint == transactionJson)
    val decodedTx = tx.convertTo[Order]
    println(decodedTx)
  }
}
