package controllers

import java.nio.ByteBuffer

import boopickle.Default._
import play.api.libs.json.Json
import play.api.mvc._
import services.ApiService
import spatutorial.shared.Api

import scala.concurrent.ExecutionContext.Implicits.global

object Router extends autowire.Server[ByteBuffer, Pickler, Pickler] {
  override def read[R: Pickler](p: ByteBuffer) = Unpickle[R].fromBytes(p)
  override def write[R: Pickler](r: R) = Pickle.intoBytes(r)
}

object Application extends Controller {
  val apiService = new ApiService()

  def index = Action {
    Ok(views.html.index("Play with Scala.js"))
  }

  def hello(name: String) = Action {
    Ok(apiService.motd(name))
  }

  def props = Action(parse.json) { request =>
    val props = for {
      key <- (request.body \ "key").asOpt[String]
    } yield apiService.property(key)

    props.map(p => Ok(Json.obj("props" -> Json.toJson(p))))
      .getOrElse(BadRequest("Invalid request - send the key"))
  }

  def autowireApi(path: String) = Action.async(parse.raw) {
    implicit request =>
      println(s"Request path: $path")

      // get the request body as Array[Byte]
      val b = request.body.asBytes(parse.UNLIMITED).get

      // call Autowire route
      Router.route[Api](apiService)(
        autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(ByteBuffer.wrap(b)))
      ).map(buffer => {
        val data = Array.ofDim[Byte](buffer.remaining())
        buffer.get(data)
        Ok(data)
      })
  }

  def logging = Action(parse.anyContent) {
    implicit request =>
      request.body.asJson.foreach { msg =>
        println(s"CLIENT - $msg")
      }
      Ok("")
  }
}
