package utils

import play.api.Logger

import scala.util.{Failure, Success, Try}

object FileUtil {

  /**
   * Read CSV file by line to Array[String], trim space & double quotes, seperated by ","
   *
   * @param filePath
   * @param logger
   * @return lines
   */
  def readCSVAndConvert(filePath: String, converter: List[Array[String]] => List[Object])(implicit logger: Logger): Try[List[Object]] = {
    Try {
      val bufferedSource = scala.io.Source.fromFile(filePath)
      val lines = bufferedSource.getLines().toList.map(
        s => s.replace("\"", "").split(",").map(_.trim)
      )
      (bufferedSource, converter(lines))
    }.toEither match {
      case Left(e) =>
        logger.error(s"Reading file $filePath error: ${e.getMessage}")
        Failure(e)
      case Right((bufferedSource, lines)) =>
        bufferedSource.close()
        Success(lines)
    }
  }
}
