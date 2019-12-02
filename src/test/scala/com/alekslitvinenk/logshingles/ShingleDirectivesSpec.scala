package com.alekslitvinenk.logshingles

import java.io.{ByteArrayOutputStream, PrintStream}

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest

import com.alekslitvinenk.logshingles.dsl.ShinglesDirectives._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ShingleDirectivesSpec extends AnyWordSpec
  with Matchers
  with ScalatestRouteTest {

  private val originalStdout = System.out
  private val okResp = "OK"

  "ShingleDirectives" should {
    "write to console" in {
      //FixMe: use  Apache Commons TeeOutputStream
      val bytesOutputStream = new ByteArrayOutputStream()
      System.setOut(new PrintStream(bytesOutputStream))

      val route = pathSingleSlash {
        get {
          complete(okResp)
        }
      }

      Get() ~> logbackShingle(route) ~> check {
        responseAs[String] shouldEqual okResp

        val logRecord = bytesOutputStream.toString

        logRecord should include("Host")
        logRecord should include("Path")
        logRecord should include("RemoteIP")
        logRecord should include("UserAgent")

        System.setOut(originalStdout)
      }
    }
  }
}