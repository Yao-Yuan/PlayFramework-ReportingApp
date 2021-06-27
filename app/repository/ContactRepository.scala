package repository

import javax.inject.{Inject, Singleton}
import models.Contact
import play.api.{Configuration, Logger}
import utils.FileUtil.readCSVAndConvert

import java.time.{Instant, LocalDateTime, ZoneId}
import scala.util.{Failure, Success}

@Singleton
class ContactRepository @Inject()(config: Configuration) extends AbstractRepository {

  implicit val logger = Logger(this.getClass)

  private var filePath: String = config.get[String]("file.path.contacts")
  private var contacts = None : Option[List[Contact]]

  override def getFilePath: String = filePath

  override def getAll: List[Contact] = contacts match {
    case None => contacts = readToObject
      contacts.get
    case Some(value) => value
  }

  override def updateAll(newFilePath : String) {
    filePath = newFilePath
    contacts = readToObject
  }

  private def readToObject = Some(readCSVAndConvert(filePath, stringsToObjects) match {
    case Failure(exception) => throw exception
    case Success(value) => value.asInstanceOf[List[Contact]]
  })

  private def stringsToObjects(lines : List[Array[String]]): List[Contact] =
    lines.tail.map{ cols =>
      Contact(cols(0).toLong,
      LocalDateTime.ofInstant(Instant.ofEpochMilli(cols(1).toLong), ZoneId.systemDefault)) //TODO: map based on col name
    }
}
