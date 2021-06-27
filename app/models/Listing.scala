package models

/**
  * Representation object of a car being sold
  */

case class Listing(
    id: Long,
    make: String, 
    price: Double,
    mileage: Long,
    seller_type: String //TODO: use enum type
    ) extends DataModel