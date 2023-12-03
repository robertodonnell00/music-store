package controllers

import models.Customer
import models.Instrument
import persistence.Serializer

/**
 * Represents the CustomerAPI class responsible for managing customer data and interactions.
 *
 * @property serializerType The type of serializer used for persistence.
 * @property serializer The serializer instance used for data serialization.
 * @property customers The list of customers managed by the CustomerAPI.
 */
class CustomerAPI(private var serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var customers = ArrayList<Customer>()

    // CRUD
    // CREATE
    /**
     * Creates a new customer and adds it to the list of customers.
     *
     * @param customer The customer object to be created and added.
     * @return `true` if the customer is added successfully, `false` otherwise.
     */
    fun create(customer: Customer): Boolean {
        return customers.add(customer)
    }

    // READ
    /**
     * Formats a list of customers into a readable string format.
     *
     * @param customerToFormat The list of customers to be formatted.
     * @return A formatted string representing the list of customers.
     */
    private fun formatListString(customerToFormat: List<Customer>): String =
        customerToFormat
            .joinToString("\n") { customer ->
                customers.indexOf(customer).toString() + ": " + customer.toString()
            }

    /**
     * Checks if the given index is a valid index within a given list.
     *
     * @param index The index to be checked.
     * @param list The list to check against.
     * @return `true` if the index is valid, `false` otherwise.
     */
    private fun isValidListIndex(index: Int, list: List<Customer>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Checks if the given index is a valid index within the list of customers.
     *
     * @param index The index to be checked.
     * @return `true` if the index is valid, `false` otherwise.
     */
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, customers)
    }

    /**
     * Retrieves a formatted string listing all customers.
     *
     * @return A formatted string listing all customers or a message indicating no customers are stored.
     */
    fun listAllCustomers(): String =
        if (customers.isEmpty()) {
            "No customers are stored"
        } else {
            formatListString(customers)
        }

    /**
     * Retrieves a string listing all instruments purchased by customers.
     *
     * @return A string listing all instruments or a message indicating no customers are stored.
     */
    fun listAllInstruments(): String =
        if (customers.isEmpty()) {
            "No customers are stored"
        } else {
            var instruments = ""
            for (customer in customers) {
                instruments += "${customer.customerName}: + ${customer.itemsBought}\n"
            }
            instruments
        }

    /**
     * Retrieves a formatted string listing all VIP customers.
     *
     * @return A formatted string listing all VIP customers or a message indicating no customers are stored.
     */
    fun listVIPCustomers(): String =
        if (customers.isEmpty()) {
            "No customers are stored"
        } else {
            formatListString(
                customers.filter { customer -> customer.vipCustomer }
            )
        }

    /**
     * Finds and returns a customer based on the provided index.
     *
     * @param index The index of the customer to find.
     * @return The found customer or `null` if the index is invalid.
     */
    fun findCustomer(index: Int): Customer? {
        return if (isValidListIndex(index, customers)) {
            customers[index]
        } else {
            null
        }
    }

    /**
     * Retrieves the total number of customers stored.
     *
     * @return The total number of customers.
     */
    fun numberOfCustomers(): Int {
        return customers.size
    }

    /**
     * Retrieves a customer based on the provided index.
     *
     * @param index The index of the customer to retrieve.
     * @return The customer at the specified index.
     */
    fun getCustomer(index: Int): Customer {
        return customers[index]
    }

    fun getAllCustomers(): ArrayList<Customer> {
        return customers
    }

    /**
     * Searches for customers by name and returns a formatted list.
     *
     * @param searchString The name to search for.
     * @return A formatted list of customers matching the provided name.
     */
    fun searchByName(searchString: String) =
        formatListString(
            customers.filter { customer -> customer.customerName.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for customers by address and returns a formatted list.
     *
     * @param searchString The address to search for.
     * @return A formatted list of customers matching the provided address.
     */
    fun searchByAddress(searchString: String) =
        formatListString(
            customers.filter { customer -> customer.customerAddress.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for customers by ID and returns a formatted list.
     *
     * @param searchInt The ID to search for.
     * @return A formatted list of customers matching the provided ID.
     */
    fun searchByID(searchInt: Int) =
        formatListString(
            customers.filter { customer -> customer.customerID == searchInt }
        )


    fun searchByItemBought(instrument: MutableSet<Instrument>) =
        formatListString(
            customers.filter { customer -> customer.itemsBought == instrument }
        )

    /**
     * Searches for customers by preferred instrument and returns a formatted list.
     *
     * @param searchString The type of instrument to search for.
     * @return A formatted list of customers with the provided preferred instrument type.
     */
    fun searchCustomersByPreferredInst(searchString: String) =
        if (customers.isEmpty()) {
            "No customers are stored"
        } else {
            formatListString(
                customers.filter { customer -> customer.preferredInstrument.contains(searchString, ignoreCase = true) }
            )
        }

    /**
     * Searches for instruments by name across all customers and returns a formatted list.
     *
     * @param searchString The name of the instrument to search for.
     * @return A formatted list of instruments matching the provided name.
     */
    fun searchInstrumentByName(searchString: String): String {
        return if (numberOfCustomers() == 0) {
            "No customers stored"
        } else {
            var listOfInstruments = ""
            for (customer in customers) {
                for (instrument in customer.itemsBought) {
                    if (instrument.instrumentName.contains(searchString, ignoreCase = true)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") {
                "No instruments found for: $searchString"
            } else {
                listOfInstruments
            }
        }
    }

    /**
     * Searches for instruments by type across all customers and returns a formatted list.
     *
     * @param searchString The type of instrument to search for.
     * @return A formatted list of instruments matching the provided type.
     */
    fun searchInstrumentByType(searchString: String): String {
        return if (numberOfCustomers() == 0) {
            "No customers stored"
        } else {
            var listOfInstruments = ""
            for (customer in customers) {
                for (instrument in customer.itemsBought) {
                    if (instrument.instrumentType.contains(searchString, ignoreCase = true)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentType} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") {
                "No instruments found for: $searchString"
            } else {
                listOfInstruments
            }
        }
    }

    /**
     * Searches for instruments by date across all customers and returns a formatted list.
     *
     * @param searchString The date to search for.
     * @return A formatted list of instruments matching the provided date.
     */
    fun searchInstrumentByDate(searchString: String): String {
        return if (numberOfCustomers() == 0) {
            "No customers stored"
        } else {
            var listOfInstruments = ""
            for (customer in customers) {
                for (instrument in customer.itemsBought) {
                    if (instrument.dateReceived.contains(searchString, ignoreCase = true)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentType} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") {
                "No instruments found for: $searchString"
            } else {
                listOfInstruments
            }
        }
    }

    /**
     * Searches for instruments by price across all customers and returns a formatted list.
     *
     * @param searchDouble The price to search for.
     * @return A formatted list of instruments matching the provided price.
     */
    fun searchInstrumentByPrice(searchDouble: Double): String {
        return if (numberOfCustomers() == 0) {
            "No customers stored"
        } else {
            var listOfInstruments = ""
            for (customer in customers) {
                for (instrument in customer.itemsBought) {
                    if (instrument.price in (searchDouble - 10.0)..(searchDouble + 10)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") {
                "No instruments found for: $searchDouble"
            } else {
                listOfInstruments
            }
        }
    }

    /**
     * Searches for instruments by review score across all customers and returns a formatted list.
     *
     * @param searchInt The review score to search for.
     * @return A formatted list of instruments matching the provided review score.
     */
    fun searchInstrumentByReview(searchInt: Int): String {
        return if (numberOfCustomers() == 0) {
            "No customers stored"
        } else {
            var listOfInstruments = ""
            for (customer in customers) {
                for (instrument in customer.itemsBought) {
                    if (instrument.instrumentReview == searchInt) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") {
                "No instruments found for: $searchInt"
            } else {
                listOfInstruments
            }
        }
    }
    /**
     * Lists instruments based on their paid status across all customers and returns a formatted list.
     *
     * @param searchBoolean The paid status to search for.
     * @return A formatted list of instruments matching the provided paid status.
     */
    fun listInstrumentsPaidFor(searchBoolean: Boolean): String {
        return if (numberOfCustomers() == 0) {
            "No customers stored"
        } else {
            var listOfInstruments = ""
            for (customer in customers) {
                for (instrument in customer.itemsBought) {
                    if (instrument.isPaidFor == searchBoolean) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") {
                "No instruments found for: $searchBoolean"
            } else {
                listOfInstruments
            }
        }
    }

//    fun listInstrumentByType(searchString: String): String {
//        return if
//    }

    // UPDATE
    /**
     * Updates customer details based on the provided index and new customer information.
     *
     * @param indexToUpdate The index of the customer to update.
     * @param customer The new customer information to update.
     * @return `true` if the update is successful, `false` otherwise.
     */
    fun updateCustomer(indexToUpdate: Int, customer: Customer?): Boolean {
        // find customer using index
        var foundCustomer = findCustomer(indexToUpdate)
        // if customer exists, use details as parameters to update customer
        if ((foundCustomer != null) && (customer != null)) {
            foundCustomer.customerID = customer.customerID
            foundCustomer.customerAddress = customer.customerAddress
            foundCustomer.customerName = customer.customerName
            foundCustomer.vipCustomer = customer.vipCustomer
            foundCustomer.preferredInstrument = customer.preferredInstrument
            if (customer.itemsBought.isNotEmpty()) {
                foundCustomer.itemsBought = customer.itemsBought
            }
            return true
        }
        return false
    }

    /**
     * Updates the VIP status of a customer based on the provided index.
     *
     * @param index The index of the customer to update.
     * @return `true` if the update is successful, `false` otherwise.
     */
    fun updateStatus(index: Int): Boolean {
        if (isValidIndex(index)) {
            val customerToUpgrade = customers[index]
            if (!customerToUpgrade.vipCustomer) {
                customerToUpgrade.vipCustomer = true
                return true
            }
        }
        return false
    }

    // DELETE

    /**
     * Deletes a customer based on the provided index.
     *
     * @param indexDelete The index of the customer to delete.
     * @return The deleted customer or `null` if the index is invalid.
     */
    fun deleteCustomer(indexDelete: Int): Customer? {
        return if (isValidListIndex(indexDelete, customers)) {
            customers.removeAt(indexDelete)
        } else {
            null
        }
    }

    // PERSISTENCE

    /**
     * Stores the current list of customers using the specified serializer.
     *
     * @throws Exception If an error occurs during the store operation.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(customers)
    }

    /**
     * Loads the list of customers using the specified serializer.
     *
     * @throws Exception If an error occurs during the load operation.
     */
    @Throws(Exception::class)
    fun load() {
        customers = serializer.read() as ArrayList<Customer>
    }

    /**
     * Changes the persistence type by updating the serializer used for storing and loading.
     *
     * @param newSerializer The new serializer to use for persistence.
     */
    fun changePersistenceType(newSerializer: Serializer) {
        serializer = newSerializer
    }
}
