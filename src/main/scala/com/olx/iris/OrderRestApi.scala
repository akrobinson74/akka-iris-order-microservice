package com.olx.iris
import akka.http.scaladsl.server.Directives
import com.spr.akka.AkkaConfiguration
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation

trait OrderService extends AkkaConfiguration {
  import com.olx.iris.model.TransactionEntity

  priv
}

trait OrderRestApi extends Directives with FailFastCirceSupport with AutoDerivation with OrderService {

}
