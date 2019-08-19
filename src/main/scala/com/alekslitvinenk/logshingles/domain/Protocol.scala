package com.alekslitvinenk.logshingles.domain

import java.sql.Timestamp
import java.time.Instant

object Protocol {
  case class Hit(
    host: String,
    path: String,
    ip: String,
    userAgent: String,
    createdAt: Timestamp = Timestamp.from(Instant.now()),
    id: Long = 0L,
  ) {
    override def toString: String = {
      s"""
        |Host: $host,
        |Path: $path,
        |RemoteIP: $ip,
        |UserAgent: $userAgent
      """.stripMargin
    }
  }
}
