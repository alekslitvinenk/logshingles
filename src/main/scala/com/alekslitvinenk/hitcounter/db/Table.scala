package com.alekslitvinenk.hitcounter.db

import com.alekslitvinenk.hitcounter.domain.Protocol
import java.sql.Timestamp

object Table {
  object MySQL {
    import slick.jdbc.MySQLProfile.api._

    class HitTable(tag: Tag) extends Table[Protocol.Hit](tag, "hitcounter") {

      def host = column[String]("host", O.Length(128))
      def path = column[String]("path", O.Length(512))
      def ip = column[String]("client_ip", O.Length(30))
      def userAgent = column[String]("user_agent", O.Length(512))
      def createdAt = column[Timestamp]("created", O.SqlType("timestamp default now()"))
      def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

      override def * = (
        host, path, ip, userAgent,createdAt, id
      ) <> (Protocol.Hit.tupled, Protocol.Hit.unapply)
    }

    lazy val hitTable = TableQuery[HitTable]
  }
}
