package repository

import config.TestConfig
import org.scalatestplus.play.PlaySpec

class ContactRepositorySpec extends PlaySpec with TestConfig{
  val contactRepository: ContactRepository = new ContactRepository(configuration)

  ".getAll" must {

    "return valid list of Contacts" in {
      val list = contactRepository.getAll
      list.length mustBe testContactsNumber
      list(0).listingId mustBe testContactsListFirstId
    }
  }

  ".updateAll" must {

    "update filePath and contacts" in {
      contactRepository.updateAll(newContactFilePath)
      contactRepository.getFilePath mustBe newContactFilePath
      val list = contactRepository.getAll
      list.length mustBe testNewContactsNumber
      list(0).listingId mustBe testNewContactsFirstId
    }
  }
}
