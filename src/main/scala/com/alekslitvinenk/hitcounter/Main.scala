package com.alekslitvinenk.hitcounter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.alekslitvinenk.hitcounter.dsl.HitCounterDirectives._

import scala.concurrent.ExecutionContext
import scala.util.Try

object Main extends App {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  private val interface = Try(args(0)).getOrElse("localhost")
  private val port = Try(args(0).toInt).getOrElse(8080)

  val route =
    get {
      extractRequest { request =>
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, request.uri.path.toString()))
      }
    }

  private val clientRouteHit= insertRequestIntoMySqlTable(logHit(route))

  Http().bindAndHandle(clientRouteHit, interface, port)
}
