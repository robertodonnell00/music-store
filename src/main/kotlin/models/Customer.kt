package models

class Customer(
    var customerID: String,
    var customerName: String,
    var customerAddress: String,
    var itemsBought: Instrument,
    var vipCustomer: Boolean,
    var preferredInstrument: String)  {
    override fun toString(): String {
        return "Note(customerID='$customerID', customerName=$customerName, customerAddress='$customerAddress', itemsBought=$itemsBought, vipCustomer= $vipCustomer, preferredInstrument= $preferredInstrument)"
    }
}