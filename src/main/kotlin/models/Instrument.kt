package models

import java.util.Date

class Instrument(
    var instrumentID: Int,
    var instrumentType: String,
    var price: Double,
    var qauntityBought: Int,
    var isPaidFor: Boolean = true,
    var instrumentReview: Int, // x out of 10
    var dateReceived: String,
    var customerBoughtID: Customer
) {
    override fun toString(): String {
        return "Note(instrumentID='$instrumentID', instrumentType=$instrumentType, price='$price', qauntityBought=$qauntityBought, isPaidFor= $isPaidFor, instrumentReview= $instrumentReview, dateReceived= $dateReceived, customerBought= $customerBoughtID)"
    }
}