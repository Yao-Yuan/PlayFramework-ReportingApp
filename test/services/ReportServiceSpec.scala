package services

import config.TestConfig
import org.scalatestplus.play.PlaySpec
import repository.{ContactRepository, ListingRepository}

class ReportServiceSpec extends PlaySpec with TestConfig{
  val reportService: ReportService = new ReportServiceImpl(new ListingRepository(configuration), new ContactRepository(configuration))

  ".getAveragePricePerSellerType" must {
    "return average prices of all listings per seller type" in {
      val prices = reportService.getAveragePricePerSellerType
      prices.get("private") must contain(listingPriceOfPrivateSeller)
    }
  }

  ".getPercentageOfCarsByMake" must {
    "return percentage of cars by make" in {
      val percentages = reportService.getPercentageOfCarsByMake
      percentages.get("VW") must contain(percentageOfVW)
    }
  }

  ".getAveragePriceOfMostContactedListing" must {
    "return average price of most contacted listing" in {
      val price = reportService.getAveragePriceOfMostContactedListing(0.5)
      price mustBe priceOfMostContactedListing
    }
  }

  ".getMostContactedListingPerMonth" must {
    "return most contacted listing per month" in {
      val listings = reportService.getMostContactedListingPerMonth(2)
      listings.get(testLocalDate).get(0)._1.make mustBe mostContactedListingMake
    }
  }
}
