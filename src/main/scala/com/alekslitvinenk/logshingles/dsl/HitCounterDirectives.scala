package com.alekslitvinenk.logshingles.dsl

import akka.http.scaladsl.server.Directives.{extractClientIP, extractLog, extractRequest, pass}
import akka.http.scaladsl.server.{Directive0, Directive1}
import com.alekslitvinenk.logshingles.domain.Protocol.Hit
import com.alekslitvinenk.logshingles.db.Table.MySQL._
import slick.jdbc.MySQLProfile.api._

object HitCounterDirectives {

  private val DB = Database.forConfig("slick")

  //FixMe: make the extraction lazy per request
  private def extractHit: Directive1[Hit] =
    extractRequest.flatMap { request =>
      extractClientIP.map { ip =>

        val headersMap = request.headers
          .map { h =>
            h.lowercaseName -> h.value()
          }.toMap
          .withDefaultValue("unknown")

        Hit(
          host = headersMap("host"),
          path = request.uri.path.toString(),
          ip = ip.toOption.map(_.getHostAddress).getOrElse("unknown"),
          userAgent = headersMap("user-agent"),
        )
      }
    }

  def insertRequestIntoMySqlTable: Directive0 =
    extractHit.flatMap { hit =>
      DB.run(
        DBIO.seq(
          hitTable.schema.createIfNotExists,
          hitTable += hit,
        )
      )

      pass
    }

  def logHit: Directive0 =
    extractLog.flatMap { log =>
      extractHit.flatMap { hit =>
        log.info(hit.toString)
        pass
      }
    }
}
