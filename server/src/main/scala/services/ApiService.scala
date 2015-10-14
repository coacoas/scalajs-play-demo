package services

import java.util.{UUID, Date}

import spatutorial.shared._
import scala.collection.JavaConverters
import JavaConverters._

class ApiService extends Api {
  override def motd(name: String): String = s"Welcome to Scala.js, $name! Time is now ${new Date}"

  override def property(name: String): List[String] =
    System.getProperties.keySet().asScala
      .collect { case k: String if k.startsWith(name) => k}
      .map((k: String) => s"$k: ${System.getProperty(k)}").toList.sorted
}
