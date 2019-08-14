package com.alekslitvinenk.hitcounter.db

import com.alekslitvinenk.hitcounter.domain.Protocol

object Table {
  object MySQL {
    import slick.jdbc.MySQLProfile.api._

    class HitTable(tag: Tag) extends Table[Protocol.Hit](tag, "hitcounter") {
      override def * = ???
    }

    lazy val hitTable = TableQuery[HitTable]
  }
}
