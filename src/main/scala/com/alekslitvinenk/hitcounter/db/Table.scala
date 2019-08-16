package com.alekslitvinenk.hitcounter.db

import com.alekslitvinenk.hitcounter.domain.Protocol

object Table {
  object MySQL {
    import slick.jdbc.MySQLProfile.api._

    class HitTable(tag: Tag) extends Table[Protocol.Hit](tag, "hitcounter") {

      def host = column[String]("host", O.Length(128))
      def path = column[String]("path", O.Length(512))
      def ip = column[String]("client_ip", O.Length(30))
      def userAgent = column[String]("user_agent", O.Length(512))
      def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

      override def * = (
        host, path, ip, userAgent
      ) <> (Protocol.Hit.tupled, Protocol.Hit.unapply)
    }

    lazy val hitTable = TableQuery[HitTable]
  }
}
