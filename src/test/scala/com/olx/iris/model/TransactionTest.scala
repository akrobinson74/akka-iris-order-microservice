package com.olx.iris.model
import org.scalatest.FunSuite

class TransactionTest extends FunSuite {

  test("Kotlin Iris payload can be deserialized") {
    val transactionJson = """{
 |  "orderId": "{{orderId}}",
 |  "source": "ONLINE",
 |  "type": "PACKAGE_PURCHASE",
 |  "customer": {
 |    "type": "BUSINESS",
 |    "emailAddress": "frida_business@example.org",
 |    "firstName": "Frida Business",
 |    "lastName": "Small",
 |    "customerName": "Frida Business Small",
 |    "businessName": "OLX",
 |    "userId": "IRIS_INTEGRATION_TEST_BUSINESS",
 |    "language": "en-PK",
 |    "address": {
 |      "street": "De Korte St",
 |      "houseNumber": "540",
 |      "zipCode": "2001",
 |      "city": "Johannesburg",
 |      "region": "Johannesburg",
 |      "state": "Gauteng",
 |      "stateCode": "StateCode",
 |      "country": "South Africa",
 |      "addressLines": ["addressLine1", "addressLine2"]
 |	},
 |    "vatNumber": "ZA4012345678"
 |  },
 |  "paymentReference": {
 |      "paymentMethod": "CREDIT_CARD",
 |      "paymentIdentifier": "123",
 |      "executionTime": "2018-08-10T15:04:23Z",
 |      "amount": {
 |        "value": 10.0,
 |        "currency": "PKR"
 |      }
 |  },
 |  "products": [
 |    {
 |      "activationTime": "2018-12-10T15:04:23Z",
 |      "category": {
 |        "level1": "87",
 |        "level2": "98",
 |        "level3": "112"
 |      },
 |      "description": "10 x 5 day boost ",
 |      "expirationTime": "2018-12-15T15:04:23Z",
 |      "grossPrice": 40.0,
 |      "name": "Advance",
 |      "orderItemId": "{{orderItemId}}",
 |      "packageId": "321",
 |      "revenueClass": "DAILY",
 |      "skuId": "543",
 |      "type": "AD_UPGRADE",
 |      "units": 1,
 |      "unitPrice": 1.0
 |    }
 |  ]
 |}
        |
      """.stripMargin
    println(transactionJson)
  }
}
