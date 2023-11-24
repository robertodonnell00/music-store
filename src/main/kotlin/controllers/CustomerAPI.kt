package controllers

import models.Customer
import persistence.Serializer

class CustomerAPI(serializerType: Serializer) {

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

    //DELETE
    fun deleteCustomer(indexDelete: Int): Customer? {
        return if(isValidListIndex(indexDelete, customers)) {
        customers.removeAt(indexDelete)
        } else null
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(customers)
    }

    @Throws(Exception::class)
    fun load() {
        customers = serializer.read() as ArrayList<Customer>
    }
}
