package com.olx.iris.akkaHttp

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.directives.DebuggingDirectives
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future

/**
  * Provides an HTTP server using a given RestApi.
  */
class RestApiServer(api: SprayRestApi)(implicit system: ActorSystem, materializer: Materializer) {

  def bind(): Future[ServerBinding] = {
    val config = ConfigFactory.load()
    val host = config.getString("http.host")
    val port = config.getInt("http.port")
    implicit val system = this.system
    implicit val materializer = this.materializer
    val loggedRoute = DebuggingDirectives.logRequestResult(("REST Interface", Logging.DebugLevel))(api.route)
    Http().bindAndHandle(loggedRoute, host, port)
  }

}
