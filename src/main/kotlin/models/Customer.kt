package models

import persistence.Serializer


class Customer(
    var customerID: Int,
    var customerName: String,
    var customerAddress: String,
    var itemsBought: MutableSet<Instrument> = mutableSetOf(),
    var vipCustomer: Boolean,
    var preferredInstrument: String) {

    override fun toString(): String {
        return "Customer(customerID='$customerID', customerName=$customerName, customerAddress='$customerAddress', itemsBought=$itemsBought, vipCustomer= $vipCustomer, preferredInstrument= $preferredInstrument)"
    }

    private fun formatListString(instrumentToFormat: MutableSet<Instrument>): String =
            instrumentToFormat
                    .joinToString("\n") { instrument ->
                        itemsBought.indexOf(instrument).toString() + ": " + instrument.toString()
                    }


    //CREATE

    private var lastInstrument = 0
    private fun getID() = lastInstrument++

    fun create(instrument: Instrument): Boolean {
        instrument.instrumentID = getID()
        return itemsBought.add(instrument)
    }

    //READ

    fun isValidListIndex(index: Int, list: MutableSet<Instrument>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, itemsBought)
    }

    fun listInstruments(): String =
            if (itemsBought.isEmpty()) "No instruments are stored"
            else formatListString(itemsBought)

    fun listInstrumentsPaidFor(boolean: Boolean): String =
            if (itemsBought.isEmpty()) "No instruments are stored"
            else formatListString(
                    if (boolean)
                        (itemsBought.filter { instrument -> instrument.isPaidFor }).toMutableSet()
                    else
                        (itemsBought.filter { instrument -> !instrument.isPaidFor }).toMutableSet()
            )

    fun listInstrumentsByType(searchString: String): String {
        return if (itemsBought.isEmpty()) "No instruments are stored"
        else
        {
            var listOfInstruments = ""
            for (item in itemsBought) {
                if (item.instrumentType.contains(searchString, ignoreCase = true)) {
                    listOfInstruments += "${item.instrumentID}: $item"
                }
            }
            if (listOfInstruments == "") "No items found for $searchString"
            else listOfInstruments
        }
}

    fun findInstrument(id: Int): Instrument? {
        return itemsBought.find { instrument -> instrument.instrumentID == id }
    }

    fun numberOfInstruments(): Int {
        return itemsBought.size
    }
    //UPDATE
    fun updateInstrument(indexToUpdate: Int, instrument: Instrument): Boolean {
        //find instrument using index
        val foundInstrument = findInstrument(indexToUpdate)
        //if instrument exists, use details as parameters to update instrument
        if (foundInstrument != null) {
            foundInstrument.instrumentID = instrument.instrumentID
            foundInstrument.instrumentType = instrument.instrumentType
            foundInstrument.instrumentReview = instrument.instrumentReview
            foundInstrument.price = instrument.price
            foundInstrument.qauntityBought = instrument.qauntityBought
            foundInstrument.isPaidFor = instrument.isPaidFor
            foundInstrument.instrumentReview = instrument.instrumentReview
            foundInstrument.dateReceived = instrument.dateReceived
            foundInstrument.customerBoughtID = instrument.customerBoughtID
            return true
        }
        return false
    }


    //DELETE

    fun delete(id: Int): Boolean {
        return itemsBought.removeIf { instrument -> instrument.instrumentID == id }
    }


}



