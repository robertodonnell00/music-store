package models

import java.util.Date

class Instrument(
    var instrumentID: Int,
    var instrumentName: String,
    var instrumentType: String,
    var price: Double,
    var qauntityBought: Int,
    var isPaidFor: Boolean = true,
    var instrumentReview: Int, // x out of 10
    var dateReceived: String,
    var customerBoughtID: Int
) {
    override fun toString(): String {
        return "InstrumentID: '$instrumentID'  Instrument Name: '$instrumentName'" +
                "\n\tInstrument Type: $instrumentType   Price: €$price " +
                "\n\tQuantity: $qauntityBought               Paid Status: $isPaidFor" +
                "\n\tReview: $instrumentReview/100             Date Sold: $dateReceived" +
                "\n\tCustomer Bought ID: $customerBoughtID"
    }
}