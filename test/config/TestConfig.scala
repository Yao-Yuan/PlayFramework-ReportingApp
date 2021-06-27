package config

import com.typesafe.config.ConfigFactory
import models.Listing
import play.api.{Configuration, Logger}

import java.io.File
import java.time.LocalDate

trait TestConfig {
  implicit val logger = Logger(this.getClass)
  val configuration = Configuration(ConfigFactory.load(ConfigFactory.parseFile(new File("test/resources/application.test.conf"))))

  final val newListingFilePath =  "test/resources/listings_new.csv"
  final val testListingsNumber = 9
  final val testListingListFirstId = 1000
  final val testNewListingsNumber = 10
  final val testNewListingsFirstId = 1289

  final val newContactFilePath = "test/resources/contacts_new.csv"
  final val testContactsNumber = 9
  final val testContactsListFirstId = 1244
  final val testNewContactsNumber = 11
  final val testNewContactsFirstId = 1030

  final val listingPriceOfPrivateSeller = 10000.0
  final val percentageOfVW = 1.0/3
  final val priceOfMostContactedListing = 10000.0

  final val testLocalDate = LocalDate.of(2020, 6, 1)
  final val mostContactedListingMake = "VW"

  final val welcomeText = "AutoScout24 Listing Report"

  final val sampleListing = new Listing(1, "VW", 1000.0, 1000, "private")

}
