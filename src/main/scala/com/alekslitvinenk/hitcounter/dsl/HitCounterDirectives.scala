package com.alekslitvinenk.hitcounter.dsl

import akka.http.scaladsl.server.Directives.{extractClientIP, extractLog, extractRequest, pass}
import akka.http.scaladsl.server.{Directive0, Directive1}
import com.alekslitvinenk.hitcounter.domain.Protocol.Hit
import com.alekslitvinenk.hitcounter.db.Table.MySQL._

object HitCounterDirectives {

  //FixMe: make the extraction lazy per request
  private def extractHit: Directive1[Hit] =
    extractRequest.flatMap { request =>
      extractClientIP.map { ip =>

        val headersMap = request.headers
          .map { h =>
            h.lowercaseName -> h.value()
          }.toMap

        Hit(
          host = headersMap("host"),
          path = request.uri.path.toString(),
          ip = ip.toOption.map(_.getHostAddress).getOrElse("unknown"),
          userAgent = headersMap("user-agent"),
        )
      }
    }

  def insertRequestIntoMySqlTable: Directive0 = {
    import slick.jdbc.MySQLProfile.api._

    extractHit.flatMap { hit =>
      //FixMe: Call db.run
      hitTable.schema.createIfNotExists
      hitTable += hit
      pass
    }
  }

  def logHit: Directive0 =
    extractLog.flatMap { log =>
      extractHit.flatMap { hit =>
        log.info(hit.toString)
        pass
      }
    }
}
