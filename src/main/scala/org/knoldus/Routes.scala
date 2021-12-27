package org.knoldus

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path, pathPrefix}
import akka.http.scaladsl.server.Directives._


import scala.concurrent.ExecutionContext.Implicits.global

class Routes {
  val provider = new Pinpoint

  val route = {
    pathPrefix("api" / "send"){
      get{
        (path(LongNumber) | parameter(Symbol("id").as[Long])){ id =>
          complete(
            provider.makeCall("Hello from amazon Pinpoint.",id.toString).map{
              case Successful =>
                (StatusCodes.OK,"Voice message has been send successfully.")
              case Failed =>
                (StatusCodes.BadRequest,"unable to send voice message.")
            }
          )
        }
      } ~
        pathEndOrSingleSlash{
          complete(StatusCodes.BadRequest, s"Route Not found")
        }

    } ~
    pathEndOrSingleSlash{
      complete(StatusCodes.BadRequest, s"Route Not found")
    }
  }

}
