package com.alekslitvinenk.hitcounter.domain

object Protocol {
  case class Hit(
    host: String,
    path: String,
    ip: String,
    userAgent: String,
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
