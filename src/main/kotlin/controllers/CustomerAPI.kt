package controllers

import models.Customer
import persistence.Serializer

class CustomerAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var customers = ArrayList<Customer>()

    // CRUD
    //CREATE
    fun add(customer: Customer): Boolean {
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

    fun listAllCustomers(): String =
        if (customers.isEmpty()) "No customers are stored"
        else formatListString(customers)

    fun findCustomer(index: Int): Customer? {
        return if(isValidListIndex(index, customers)){
            customers[index]
        } else null
    }

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
            foundCustomer.itemsBought = customer.itemsBought
            foundCustomer.preferredInstrument = customer.preferredInstrument
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
