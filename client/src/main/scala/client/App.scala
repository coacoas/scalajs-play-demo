package client

import autowire._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData
import spatutorial.shared.Api

import scala.collection.immutable.Iterable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs
import scala.scalajs.js
import scala.scalajs.js.{JSON, JSApp}
import scala.scalajs.js.annotation.JSExport
import scalatags.Text.TypedTag
import scalatags.Text.all._

import org.scalajs.jquery.{jQuery => $, JQueryEventObject}

import boopickle.Default._

@JSExport
object App extends JSApp {
  val pickle = stringPickler

  override def main(): Unit = {
    $(document).ready {
//      Ajax.get("/api/hello/JaxNode").foreach { xhr =>
//        $(document.body).html(b(xhr.responseText).render)
//      }



      val input = $("#input")
      input.keyup((e: Event) => {
        val text = input.value().asInstanceOf[String]
        if (!text.isEmpty) {
//          val query = js.Dynamic.literal(key = text)
//          Ajax.post("/api/props", JSON.stringify(query),
//            headers = Map("Content-Type" -> "application/json")).map { xhr =>
//            if (xhr.status == 200) {
//              val result = JSON.parse(xhr.responseText).props.asInstanceOf[js.Dictionary[String]]
//              for ((key, prop) <- result) yield li(b(key), ": ", em(prop))
//            } else {
//              List(li(s"The call failed: ${xhr.responseText}"))
//            }
//          }.foreach { items =>
//            $("#result").html(ul(items.toList).render)
//          }

          AjaxClient[Api].property(text).call().foreach { props =>
            $("#result").html(ul(
              (for ((key, prop) <- props) yield li(b(key), ": ", em(prop))).toSeq
            ).render)
          }
        } else {
          $("#result").html("Please enter a query in the text box")
        }
      })
    }
  }
}
