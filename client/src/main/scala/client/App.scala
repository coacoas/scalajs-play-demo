package client

import autowire._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import spatutorial.shared.Api

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.Text.all._

import org.scalajs.jquery.{jQuery => $, JQueryEventObject}

import boopickle.Default._

/**
 * Created by bcarlson on 10/13/15.
 */
@JSExport
object App extends JSApp {
  val pickle = stringPickler

  override def main(): Unit = {
    $(document).ready {
      Ajax.get("/api/hello/JaxJUG").foreach { xhr =>
        $(document.body).html(b(xhr.responseText).render)
      }
    }

//    $(document).ready {
//      val input = $("#input")
//      input.keyup((e: Event) => {
//        val text = input.value().asInstanceOf[String]
//        if (!text.isEmpty) {
//          AjaxClient[Api].property(text).call().foreach { props =>
//            $("#result").html(ul(props.map(li(_))).render)
//          }
//        } else {
//          $("#result").html("Please enter a query in the text box")
//        }
//      })
//    }
  }
}
