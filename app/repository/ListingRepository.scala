package repository

import models.Listing
import play.api.{Configuration, Logger}
import utils.FileUtil.readCSVAndConvert

import javax.inject.{Inject, Singleton}
import scala.util.{Failure, Success}

@Singleton
class ListingRepository @Inject()(config: Configuration) extends AbstractRepository{

  implicit val logger = Logger(this.getClass)
  private var filePath: String = config.get[String]("file.path.listings")
  private var listings = None : Option[List[Listing]]

  override def getFilePath = filePath

  override def getAll: List[Listing] = listings match {
    case None => {
      listings = readToList
      listings.get
    }
    case Some(value) => value
  }

  override def updateAll(newFilePath : String) = {
    filePath = newFilePath
    listings = readToList
  }

  private def readToList = Some(readCSVAndConvert(filePath, stringsToObjects) match {
    case Failure(exception) => throw exception
    case Success(value) => value.asInstanceOf[List[Listing]]
  })

  private def stringsToObjects(lines : List[Array[String]]): List[Listing] = {
    lines.tail.map{ cols =>
      Listing(cols(0).toLong, cols(1), cols(2).toDouble, cols(3).toLong, cols(4)) //TODO: map based on col name
    }
  }
}
