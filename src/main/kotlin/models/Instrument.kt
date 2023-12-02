package models

class Instrument(
    var instrumentID: Int = 0,
    var instrumentName: String = "",
    var instrumentType: String = "",
    var price: Double = 0.0,
    var qauntityBought: Int = 0,
    var isPaidFor: Boolean = true,
    var instrumentReview: Int = 0, // x out of 10
    var dateReceived: String = "",
    var customerBoughtID: Int = 0
) {
    override fun toString(): String {
        return "InstrumentID: '$instrumentID'  Instrument Name: '$instrumentName'" +
            "\n\tInstrument Type: $instrumentType   Price: €$price " +
            "\n\tQuantity: $qauntityBought               Paid Status: $isPaidFor" +
            "\n\tReview: $instrumentReview/100             Date Sold: $dateReceived" +
            "\n\tCustomer Bought ID: $customerBoughtID"
    }
}
