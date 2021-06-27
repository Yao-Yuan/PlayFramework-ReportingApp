package services

import com.google.inject.ImplementedBy
import models.{Contact, Listing}
import repository.{ContactRepository, ListingRepository}

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

@ImplementedBy(classOf[ReportServiceImpl])
trait ReportService {
  def getAveragePricePerSellerType: Map[String, Double]
  def getPercentageOfCarsByMake: Map[String, Double]
  def getAveragePriceOfMostContactedListing(topPercent: Double): Double
  def getMostContactedListingPerMonth(topCount: Int): Map[LocalDate, Seq[(Listing, Int)]]
}

@Singleton
class ReportServiceImpl @Inject()(listingRepository: ListingRepository, contactRepository: ContactRepository) extends ReportService {

  private lazy val listings : List[Listing]= listingRepository.getAll
  private lazy val contacts : List[Contact] = contactRepository.getAll

  override def getAveragePricePerSellerType: Map[String, Double] = listings.groupBy(_.seller_type)
                                                                .map(group => group._1 -> group._2.map(_.price).sum / group._2.length)

  override def getPercentageOfCarsByMake: Map[String, Double] = listings.groupBy(_.make)
                                                              .map(group => group._1 -> group._2.length.toDouble/listings.length)

  override def getAveragePriceOfMostContactedListing(topPercent: Double): Double = {
    val mostContactedListingIdsWithCount = getTopNContactedListingIdsWithContactCount((topPercent * contacts.length).toInt, contacts)

    mostContactedListingIdsWithCount.map(idWithCount => listings.find(_.id == idWithCount._1).map(listing => listing.price))
      .collect{ case Some(value) => value }
      .sum / mostContactedListingIdsWithCount.length
  }

  override def getMostContactedListingPerMonth(topCount: Int): Map[LocalDate, Seq[(Listing, Int)]] = {  //TODO: consider cannot find in Listing
    val contactsPerMonth = contacts
      .groupBy(contact => (contact.contactDate.getYear, contact.contactDate.getMonthValue))
      .map(contactPerMonth => contactPerMonth._1 -> getTopNContactedListingIdsWithContactCount(topCount, contactPerMonth._2))

    contactsPerMonth.collect{
      case (yearAndMonth, listIdsWithCount) =>
        LocalDate.of(yearAndMonth._1, yearAndMonth._2, 1) -> listIdsWithCount
          .map(idAndCount => (listings.find(_.id == idAndCount._1), idAndCount._2))
          .collect{ case (Some(listing), count) => (listing, count)}
    }
  }

  private def getTopNContactedListingIdsWithContactCount(topCount: Int, contactList: List[Contact]): Seq[(Long, Int)] = {
    contactList.groupBy(_.listingId)
      .map(group => group._1 -> group._2.length)
      .toSeq.sortBy(_._2)(Ordering[Int].reverse).take(topCount)
  }
}
