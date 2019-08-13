package com.alekslitvinenk.hitcounter

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive0, Route}
import akka.stream.ActorMaterializer
import com.alekslitvinenk.hitcounter.domain.Protocol.Hit

import scala.concurrent.ExecutionContext
import scala.util.Try

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val log = Logging(system, this.getClass)

  val interface = Try(args(0)).getOrElse("localhost")
  val port = Try(args(0).toInt).getOrElse(8080)

  private def logRequest(r: Route): Directive0 =
    extractRequest.flatMap { request =>
      extractClientIP.flatMap { ip =>

        val headersMap = request.headers
          .map { h =>
            h.lowercaseName -> h.value()
          }.toMap

        val hit = Hit(
          host = headersMap("host"),
          path = request.uri.path.toString(),
          ip = ip.toOption.map(_.getHostAddress).getOrElse("unknown"),
          userAgent = headersMap("user-agent"),
        )

        log.info(hit.toString)
        pass
      }
    }

  val route =
    get {
      extractRequest { request =>
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, request.uri.path.toString()))
      }
    }

  val clientRouteLogged = logRequest(route)(route)

  Http().bindAndHandle(clientRouteLogged, interface, port)
}
