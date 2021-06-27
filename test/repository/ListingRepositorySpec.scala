package repository

import config.TestConfig
import org.scalatestplus.play.PlaySpec

class ListingRepositorySpec extends PlaySpec with TestConfig{
  val listingRepository: ListingRepository = new ListingRepository(configuration)

  ".getAll" must {

    "return valid list of Listings" in {
      val list = listingRepository.getAll
      list.length mustBe testListingsNumber
      list(0).id mustBe testListingListFirstId
    }
  }

  ".updateAll" must {

    "update filePath and listings" in {
      listingRepository.updateAll(newListingFilePath)
      listingRepository.getFilePath mustBe newListingFilePath
      val list = listingRepository.getAll
      list.length mustBe testNewListingsNumber
      list(0).id mustBe testNewListingsFirstId
    }
  }

}
