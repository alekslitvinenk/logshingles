package com.alekslitvinenk.hitcounter.domain

object Protocol {
  case class Hit(
    host: String,
    path: String,
    ip: String,
    userAgent: String,
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
