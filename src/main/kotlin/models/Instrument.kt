package models

import java.util.Date

class Instrument(
    var instrumentID: String,
    var instrumentType: String,
    var price: Float,
    var qauntityBought: Int,
    var isPaidFor: Boolean,
    var instrumentReview: Int,// x out of 10
    var dateReceived: Date,
    var customerBought: Customer
) {
    override fun toString(): String {
        return "Note(instrumentID='$instrumentID', instrumentType=$instrumentType, price='$price', qauntityBought=$qauntityBought, isPaidFor= $isPaidFor, instrumentReview= $instrumentReview, dateReceived= $dateReceived, customerBought= $customerBought)"
    }
}