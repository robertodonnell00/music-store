package controllers

import models.Customer
import models.Instrument
import persistence.Serializer

class CustomerAPI(private var serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var customers = ArrayList<Customer>()

    // CRUD
    //CREATE
    fun create(customer: Customer): Boolean {
        return customers.add(customer)

    }

    //READ
    private fun formatListString(customerToFormat: List<Customer>): String =
        customerToFormat
            .joinToString("\n") { customer ->
                customers.indexOf(customer).toString() + ": " + customer.toString()
            }

    fun isValidListIndex(index: Int, list: List<Customer>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, customers)
    }

    fun listAllCustomers(): String =
        if (customers.isEmpty()) "No customers are stored"
        else formatListString(customers)

    fun listAllInstruments(): String =
        if(customers.isEmpty()) "No customers are stored"
        else {
            var instruments = ""
            for (customer in customers){
                instruments += "${customer.customerName}: + ${customer.itemsBought}\n"
            }
            instruments
        }

    fun listVIPCustomers(): String =
        if(customers.isEmpty()) "No customers are stored"
        else formatListString(
            customers.filter { customer -> customer.vipCustomer == true }
        )



    fun findCustomer(index: Int): Customer? {
        return if(isValidListIndex(index, customers)){
            customers[index]
        } else null
    }

    fun numberOfCustomers(): Int {
        return customers.size
    }

    fun getCustomer(index: Int): Customer {
        return customers[index]
    }

    fun searchByName(searchString: String)=
        formatListString(
            customers.filter { customer -> customer.customerName.contains(searchString, ignoreCase = true) }
        )

    fun searchByAddress(searchString: String)=
        formatListString(
            customers.filter { customer -> customer.customerAddress.contains(searchString,ignoreCase = true)}
        )


    fun searchByID(searchInt: Int)=
        formatListString(
            customers.filter { customer -> customer.customerID.equals(searchInt)  }
        )

    fun searchByItemBought(instrument: MutableSet<Instrument>) =
        formatListString(
            customers.filter { customer -> customer.itemsBought.equals(instrument) }
        )

    fun searchCustomersByPreferredInst(searchString: String) =
        if(customers.isEmpty()) "No customers are stored"
        else formatListString(
            customers.filter { customer -> customer.preferredInstrument.contains(searchString, ignoreCase = true) }
        )

    fun searchInstrumentByName(searchString: String): String {
        return if (numberOfCustomers() == 0) "No customers stored"
        else {
            var listOfInstruments = ""
            for (customer in customers) {
                for(instrument in customer.itemsBought) {
                    if(instrument.instrumentName.contains(searchString, ignoreCase = true)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") "No instruments found for: $searchString"
            else listOfInstruments
        }
    }

    fun searchInstrumentByType(searchString: String): String {
        return if (numberOfCustomers() == 0) "No customers stored"
        else {
            var listOfInstruments = ""
            for (customer in customers) {
                for(instrument in customer.itemsBought) {
                    if(instrument.instrumentType.contains(searchString, ignoreCase = true)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentType} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") "No instruments found for: $searchString"
            else listOfInstruments
        }
    }

    fun searchInstrumentByDate(searchString: String): String {
        return if (numberOfCustomers() == 0) "No customers stored"
        else {
            var listOfInstruments = ""
            for (customer in customers) {
                for(instrument in customer.itemsBought) {
                    if(instrument.dateReceived.contains(searchString, ignoreCase = true)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentType} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") "No instruments found for: $searchString"
            else listOfInstruments
        }
    }

    fun searchInstrumentByPrice(searchDouble: Double): String {
        return if (numberOfCustomers() == 0) "No customers stored"
        else {
            var listOfInstruments = ""
            for (customer in customers) {
                for(instrument in customer.itemsBought) {
                    if(instrument.price in (searchDouble - 10.0)..(searchDouble +10)) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") "No instruments found for: $searchDouble"
            else listOfInstruments
        }
    }

    fun searchInstrumentByReview(searchInt: Int): String {
        return if (numberOfCustomers() == 0) "No customers stored"
        else {
            var listOfInstruments = ""
            for (customer in customers) {
                for(instrument in customer.itemsBought) {
                    if(instrument.instrumentReview == searchInt) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") "No instruments found for: $searchInt"
            else listOfInstruments
        }
    }
    fun listInstrumentsPaidFor(searchBoolean: Boolean): String {
        return if (numberOfCustomers() == 0) "No customers stored"
        else {
            var listOfInstruments = ""
            for (customer in customers) {
                for(instrument in customer.itemsBought) {
                    if(instrument.isPaidFor == searchBoolean) {
                        listOfInstruments += "${customer.customerID}: ${customer.customerName}\n${instrument.instrumentID}: ${instrument.instrumentName} \n\t${instrument}\n"
                    }
                }
            }
            if (listOfInstruments == "") "No instruments found for: $searchBoolean"
            else listOfInstruments
        }
    }

//    fun listInstrumentByType(searchString: String): String {
//        return if
//    }



    //UPDATE
    fun updateCustomer(indexToUpdate: Int, customer: Customer?): Boolean {
        //find customer using index
        var foundCustomer = findCustomer(indexToUpdate)
        //if customer exists, use details as parameters to update customer
        if ((foundCustomer != null) && (customer != null)) {
            foundCustomer.customerID = customer.customerID
            foundCustomer.customerAddress = customer.customerAddress
            foundCustomer.customerName = customer.customerName
            foundCustomer.vipCustomer = customer.vipCustomer
            foundCustomer.preferredInstrument = customer.preferredInstrument
            if(!customer.itemsBought.isEmpty()){
                foundCustomer.itemsBought = customer.itemsBought
            }
            return true
        }
        return false
    }

    fun updateStatus(index: Int): Boolean {
        if(isValidIndex(index)){
            val customerToUpgrade = customers[index]
            if (!customerToUpgrade.vipCustomer){
                customerToUpgrade.vipCustomer = true
                return true
            }
        }
        return false
    }

    //DELETE
    fun deleteCustomer(indexDelete: Int): Customer? {
        return if(isValidListIndex(indexDelete, customers)) {
        customers.removeAt(indexDelete)
        } else null
    }

    //PERSISTENCE
    @Throws(Exception::class)
    fun store() {
        serializer.write(customers)
    }

    @Throws(Exception::class)
    fun load() {
        customers = serializer.read() as ArrayList<Customer>
    }


    fun changePersistenceType(newSerializer: Serializer){
        serializer = newSerializer
    }
}
