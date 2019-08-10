package com.alekslitvinenk.hitcounter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, extractClientIP, extractRequest, get}
import akka.stream.ActorMaterializer
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
        //request.headers.foreach(println(_))
        //println(request.uri.path)
        extractClientIP { ip =>
          //ip.toOption.map(_.getHostAddress).getOrElse("unknown")
          //withLog(special) {
          log.info("req")
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "OK"))
          //}
        }
      }
    }

  Http().bindAndHandle(route, interface, port)
}
