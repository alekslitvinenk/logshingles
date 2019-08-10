package com.alekslitvinenk.hitcounter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, extractClientIP, extractRequest, get}
import akka.stream.ActorMaterializer
import com.alekslitvinenk.hitcounter.domain.Protocol.Hit
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.util.Try

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val interface = Try(args(0)).getOrElse("localhost")
  val port = Try(args(0).toInt).getOrElse(8080)

  val log = LoggerFactory.getLogger("Main")

  val route =
    get {
      extractRequest { request =>
        val headersMap = request.headers
          .map { h =>
            h.lowercaseName -> h.value()
          }.toMap

        extractClientIP { ip =>

          val hit = Hit(
            host = headersMap("host"),
            path = request.uri.path.toString(),
            ip = ip.toOption.map(_.getHostAddress).getOrElse("unknown"),
            userAgent = headersMap("user-agent"),
          )

          log.info(hit.toString)
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, request.uri.path.toString()))
        }
      }
    }

  Http().bindAndHandle(route, interface, port)
}
