package controllers

import config.TestConfig
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.Status.OK
import play.api.test.Helpers.{GET, contentAsString, contentType, defaultAwaitTimeout, route, status, stubControllerComponents, writeableOf_AnyContentAsEmpty}
import play.api.test.{FakeRequest, Injecting}
import services.{ReportServiceImpl}

import java.time.LocalDate

class ReportControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar with TestConfig {


    "ReportController GET" should {
      val mockService = mock[ReportServiceImpl]
      when(mockService.getMostContactedListingPerMonth(5)).thenReturn(Map(LocalDate.now() -> List((sampleListing, 1))))
      when(mockService.getAveragePricePerSellerType).thenReturn(Map(mostContactedListingMake ->priceOfMostContactedListing))
      when(mockService.getAveragePriceOfMostContactedListing(0.3)).thenReturn(priceOfMostContactedListing)
      when(mockService.getPercentageOfCarsByMake).thenReturn(Map(mostContactedListingMake -> percentageOfVW))

      "render the index page from a new instance of controller" in {

            val controller = new ReportController(stubControllerComponents(), mockService)
            val report = controller.index().apply(FakeRequest(GET, "/"))

            status(report) mustBe OK
            contentType(report) mustBe Some("text/html")
            contentAsString(report) must include (welcomeText)
        }

        "render the report page from the application" in {
            val controller = inject[ReportController]
            val report = controller.index().apply(FakeRequest(GET, "/"))

            status(report) mustBe OK
            contentType(report) mustBe Some("text/html")
            contentAsString(report) must include (welcomeText)
        }

        "render the index page from the router" in {
          val request = FakeRequest(GET, "/")
          val report = route(app, request).get

          status(report) mustBe OK
          contentType(report) mustBe Some("text/html")
          contentAsString(report) must include (welcomeText)
        }


    }
}
