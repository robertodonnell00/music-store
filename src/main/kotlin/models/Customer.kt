package models

/**
 * Represents a customer in the system with associated information such as ID, name, address, purchased instruments,
 * VIP status, and preferred instrument type.
 *
 * @property customerID The unique identifier for the customer.
 * @property customerName The name of the customer.
 * @property customerAddress The address of the customer.
 * @property itemsBought The set of instruments purchased by the customer.
 * @property vipCustomer Indicates whether the customer has VIP status.
 * @property preferredInstrument The preferred type of instrument for the customer.
 */
class Customer(
    var customerID: Int = 0,
    var customerName: String = "",
    var customerAddress: String = "",
    var itemsBought: MutableSet<Instrument> = mutableSetOf(),
    var vipCustomer: Boolean = false,
    var preferredInstrument: String = ""
) {

    /**
     * Returns a formatted string representation of the customer, including details such as ID, name, address,
     * purchased instruments, VIP status, and preferred instrument type.
     */
    override fun toString(): String {
        return "Customer ID: '$customerID'           Name: $customerName" +
            "\n\tcustomerAddress: $customerAddress" +
            "\n\titemsBought:\n\t$itemsBought    " +
            "\n\tVIP Status: $vipCustomer    Preferred Instrument Type: $preferredInstrument"
    }

    /**
     * Formats a list of instruments into a string representation, including their indices and details.
     *
     * @param instrumentToFormat The set of instruments to format.
     * @return A formatted string representing the list of instruments.
     */
    private fun formatListString(instrumentToFormat: MutableSet<Instrument>): String =
        instrumentToFormat
            .joinToString("\n") { instrument ->
                itemsBought.indexOf(instrument).toString() + ": " + instrument.toString()
            }

    // CREATE
    /**
     * Gets the next available ID for an instrument.
     *
     * @return The next available instrument ID.
     */
    private var lastInstrument = 0
    private fun getID() = lastInstrument++

    /**
     * Creates a new instrument and adds it to the list of items bought by the customer.
     *
     * @param instrument The instrument to be added.
     * @return `true` if the instrument is added successfully, `false` otherwise.
     */
    fun create(instrument: Instrument): Boolean {
        instrument.instrumentID = getID()
        return itemsBought.add(instrument)
    }

    // READ
    /**
     * Checks if the provided index is a valid index within the given list of instruments.
     *
     * @param index The index to check.
     * @param list The list of instruments to check against.
     * @return `true` if the index is valid, `false` otherwise.
     */
    fun isValidListIndex(index: Int, list: MutableSet<Instrument>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Checks if the provided index is a valid index within the list of items bought by the customer.
     *
     * @param index The index to check.
     * @return `true` if the index is valid, `false` otherwise.
     */
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, itemsBought)
    }

    /**
     * Returns a formatted string listing all instruments purchased by the customer.
     *
     * @return A string representation of purchased instruments.
     */
    fun listInstruments(): String =
        if (itemsBought.isEmpty()) {
            "No instruments are stored"
        } else {
            formatListString(itemsBought)
        }

    /**
     * Returns a formatted string listing instruments based on whether they are paid for or not.
     *
     * @param boolean Indicates whether to list paid or unpaid instruments.
     * @return A string representation of instruments based on payment status.
     */
    fun listInstrumentsPaidFor(boolean: Boolean): String =
        if (itemsBought.isEmpty()) {
            "No instruments are stored"
        } else {
            formatListString(
                if (boolean) {
                    (itemsBought.filter { instrument -> instrument.isPaidFor }).toMutableSet()
                } else {
                    (itemsBought.filter { instrument -> !instrument.isPaidFor }).toMutableSet()
                }
            )
        }

    /**
     * Returns a formatted string listing instruments of a specific type.
     *
     * @param searchString The type of instruments to search for.
     * @return A string representation of instruments matching the specified type.
     */
    fun listInstrumentsByType(searchString: String): String {
        return if (itemsBought.isEmpty()) {
            "No instruments are stored"
        } else {
            var listOfInstruments = ""
            for (item in itemsBought) {
                if (item.instrumentType.contains(searchString, ignoreCase = true)) {
                    listOfInstruments += "${item.instrumentID}: $item"
                }
            }
            if (listOfInstruments == "") {
                "No items found for $searchString"
            } else {
                listOfInstruments
            }
        }
    }

    /**
     * Searches for an instrument by its ID and returns a formatted string representation.
     *
     * @param searchInt The ID of the instrument to search for.
     * @return A string representation of the found instrument.
     */
    fun searchInstrumentByID(searchInt: Int) =
        if (itemsBought.isEmpty()) {
            "No instruments stored"
        } else {
            formatListString(
                (itemsBought.filter { instrument -> instrument.instrumentID == searchInt }).toMutableSet()
            )
        }

    /**
     * Searches for instruments based on the quantity bought and returns a formatted string representation.
     *
     * @param searchInt The quantity of instruments to search for.
     * @return A string representation of instruments matching the specified quantity.
     */
    fun searchByQuantityBought(searchInt: Int) =
        if (itemsBought.isEmpty()) {
            "No instruments stored"
        } else {
            formatListString(
                (itemsBought.filter { instrument -> instrument.qauntityBought == searchInt }).toMutableSet()
            )
        }


    /**
     * Searches for instruments based on the date received and returns a formatted string representation.
     *
     * @param searchString The date of instruments to search for.
     * @return A string representation of instruments matching the specified date.
     */
    fun searchByDateReceived(searchString: String) =
        if (itemsBought.isEmpty()) {
            "No instruments stored"
        } else {
            formatListString(
                (itemsBought.filter { instrument -> instrument.dateReceived == searchString }).toMutableSet()
            )
        }

    /**
     * Searches for instruments based on the review and returns a formatted string representation.
     *
     * @param searchInt The review score of instruments to search for.
     * @return A string representation of instruments matching the specified review score.
     */
    fun searchByReview(searchInt: Int) =
        if (itemsBought.isEmpty()) {
            "No instruments stored"
        } else {
            formatListString(
                (itemsBought.filter { instrument -> instrument.instrumentReview == searchInt }).toMutableSet()
            )
        }

    /**
     * Searches for instruments based on the name and returns a formatted string representation.
     *
     * @param searchString The name of instruments to search for.
     * @return A string representation of instruments matching the specified name.
     */
    fun searchByInstrumentName(searchString: String) =
        if (itemsBought.isEmpty()) {
            "No instruments stored"
        } else {
            formatListString(
                (itemsBought.filter { instrument -> instrument.instrumentName.contains(searchString, ignoreCase = true) }).toMutableSet()
            )
        }

    /**
     * Searches for instruments based on the price range and returns a formatted string representation.
     *
     * @param searchDouble The price of instruments to search for.
     * @return A string representation of instruments matching the specified price range.
     */
    fun searchByPrice(searchDouble: Double) =
        if (itemsBought.isEmpty()) {
            "No instruments stored"
        } else {
            formatListString(
                (itemsBought.filter { instrument -> instrument.price in (searchDouble - 10.0)..(searchDouble + 10.0) }).toMutableSet()
            )
        }

    /**
     * Finds an instrument by its ID and returns the corresponding [Instrument] object.
     *
     * @param id The ID of the instrument to find.
     * @return The found instrument, or `null` if not found.
     */
    fun findInstrument(id: Int): Instrument? {
        return itemsBought.find { instrument -> instrument.instrumentID == id }
    }


    /**
     * Returns the total number of instruments purchased by the customer.
     *
     * @return The total number of instruments.
     */
    fun numberOfInstruments(): Int {
        return itemsBought.size
    }

    // UPDATE
    /**
     * Updates the details of an instrument associated with the customer.
     *
     * @param indexToUpdate The index of the instrument to update.
     * @param instrument The [Instrument] object containing updated details.
     * @return `true` if the update is successful, `false` otherwise.
     */
    fun updateInstrument(indexToUpdate: Int, instrument: Instrument): Boolean {
        // find instrument using index
        val foundInstrument = findInstrument(indexToUpdate)
        // if instrument exists, use details as parameters to update instrument
        if (foundInstrument != null) {
            foundInstrument.instrumentID = instrument.instrumentID
            foundInstrument.instrumentName = instrument.instrumentName
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

    // DELETE

    /**
     * Deletes an instrument from the customer's purchased items based on its ID.
     *
     * @param id The ID of the instrument to delete.
     * @return `true` if the instrument is successfully deleted, `false` otherwise.
     */
    fun delete(id: Int): Boolean {
        return itemsBought.removeIf { instrument -> instrument.instrumentID == id }
    }
}
