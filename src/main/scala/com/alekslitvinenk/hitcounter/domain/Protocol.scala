package com.alekslitvinenk.hitcounter.domain

object Protocol {
  case class Hit(
    host: String,
    path: String,
    ip: String,
    userAgent: String,
  )
}
