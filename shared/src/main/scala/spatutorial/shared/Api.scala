package spatutorial.shared

trait Api {
  // message of the day
  def motd(name: String): String
  def property(key: String): Map[String, String]
}
