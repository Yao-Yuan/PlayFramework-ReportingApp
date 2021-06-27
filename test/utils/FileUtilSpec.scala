package utils

import FileUtil._
import org.scalatest.TryValues._
import org.scalatestplus.play.PlaySpec
import config.TestConfig

class FileUtilSpec extends PlaySpec with TestConfig{

    ".readCSV" must {

      "return success of list of objects" in {
        val lines = readCSVAndConvert("test/resources/listings.csv", list => list)
        lines.isSuccess mustBe true
        lines.success.value.length mustBe 10
      }

      "return failure if file not exists" in {
        val lines = readCSVAndConvert("test/resources/not-exist.csv", list => list)
        lines.isSuccess mustBe false
      }

      "return failure if converter mismatches" in {
        val lines = readCSVAndConvert("test/resources/listings.csv", list => list.map{ cols => (cols(0).toInt, cols(1).toInt) })
        lines.isSuccess mustBe false
      }
    }
}
