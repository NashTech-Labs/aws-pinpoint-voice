package org.knoldus

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.util.{Failure, Success}

object HttpServer extends App {

  val host = "localhost"
  val port = 7000

  implicit val system = ActorSystem("Amazon-Pinpoint-Server")
  implicit val materializer : ActorMaterializer.type = ActorMaterializer
  import system.dispatcher


  val route = new Routes

  val binding = Http().newServerAt(host,port).bindFlow(route.route)

  binding.onComplete{
    case Success(value) =>
      println(s"Server is listening on http://$host:${port}")
    case Failure(exception) =>
      println(s"Failure : $exception")
      system.terminate()
  }

}
