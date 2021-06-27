package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import services.ReportService

@Singleton
class ReportController @Inject()(val controllerComponents: ControllerComponents, reportService: ReportService) extends BaseController {

    def index() = Action { implicit request: Request[AnyContent] => {
      val averagePricePerSellerType = reportService.getAveragePricePerSellerType
      val percentageOfCarsByMake = reportService.getPercentageOfCarsByMake
      val averagePriceOfMostContactedListing = reportService.getAveragePriceOfMostContactedListing(0.3)
      val mostContactedListingPerMonth = reportService.getMostContactedListingPerMonth(5).toSeq.sortBy(date => (date._1.getYear, date._1.getMonthValue))

            Ok(views.html.index(
              averagePricePerSellerType = averagePricePerSellerType,
              percentageOfCarsByMake = percentageOfCarsByMake,
              averagePriceOfMostContactedListing = averagePriceOfMostContactedListing,
              mostContactedListingPerMonth = mostContactedListingPerMonth))
        }
    }
}