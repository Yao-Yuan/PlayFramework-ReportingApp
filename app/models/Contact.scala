package models

import java.time.LocalDateTime

/**
 * Representation object of a car being sold
 */

case class Contact(
            listingId: Long,
            contactDate: LocalDateTime
          ) extends DataModel