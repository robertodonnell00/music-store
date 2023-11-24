package controllers

import models.Customer
import models.Instrument
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import java.io.File
import kotlin.test.assertEquals

class CustomerAPITest {
    private val customerAPI = CustomerAPI(JSONSerializer(File("customers.json")))

    private var customerFred: Customer? = null
    private var customerJoe: Customer? = null
    private var customerMary: Customer? = null
    private var customerMike: Customer? = null
    private var populatedCustomers: CustomerAPI? = CustomerAPI(JSONSerializer(File("customers.json")))
    private var emptyCustomers: CustomerAPI? = CustomerAPI(JSONSerializer(File("customers.json")))

    private var guitar: Instrument = Instrument(0, "Guitar", 300.00, 1, true, 7, "03/02/22", 0)
    private var drums: Instrument = Instrument(1, "Drums", 490.00, 1, true, 8, "31/10/22", 1)
    private var flute: Instrument = Instrument(2, "Wind", 80.00, 1, true, 4, "15/04/21", 2)
    private var piano: Instrument = Instrument(3, "Piano", 600.00, 1, true, 9, "28/06/23", 3)
    private var harp: Instrument = Instrument(4, "Strings", 300.00, 1, true, 6, "09/08/23", 3)

    @BeforeEach
    fun setup() {
        customerFred = Customer(1, "Fred", "Waterford", mutableSetOf(guitar), true, "Guitar")
        customerJoe = Customer(2, "Joe", "Kilkenny", mutableSetOf(drums), true, "Drums")
        customerMary = Customer(3, "Mary", "Waterford", mutableSetOf(flute), true, "Wind")
        customerMike = Customer(4, "Mike", "Wexford", mutableSetOf(piano, harp), true, "Piano")

        populatedCustomers!!.create(customerFred!!)
        populatedCustomers!!.create(customerJoe!!)
        populatedCustomers!!.create(customerMary!!)
        populatedCustomers!!.create(customerMike!!)

    }


    @AfterEach
    fun tearDown() {
        customerFred = null
        customerJoe = null
        customerMary = null
        customerMike = null
        populatedCustomers = null
        emptyCustomers = null
    }

    @Nested
    inner class AddCustomers {
        @Test
        fun `adding a Customer to a populated list adds to ArrayList`() {
            val newCustomer = Customer(12, "Ger", "Galway", mutableSetOf(guitar), true, "Guitar")
            assertEquals(4, populatedCustomers!!.numberOfCustomers())
            assertTrue(populatedCustomers!!.create(newCustomer))
            assertEquals(5, populatedCustomers!!.numberOfCustomers())
            assertEquals(newCustomer, populatedCustomers!!.findCustomer(populatedCustomers!!.numberOfCustomers() - 1))
        }

        @Test
        fun `adding a Customer to an empty list adds to arrayList`() {
            val newCustomer = Customer(12, "Ger", "Galway", mutableSetOf(guitar), true, "Guitar")
            assertEquals(0, emptyCustomers!!.numberOfCustomers())
            assertTrue(emptyCustomers!!.create(newCustomer))
            assertEquals(1, emptyCustomers!!.numberOfCustomers())
            assertEquals(newCustomer, emptyCustomers!!.findCustomer(emptyCustomers!!.numberOfCustomers() - 1))
        }
    }

    @Nested
    inner class ListCustomers {

        @Test
        fun `listAllCustomers Test for no customers stored`() {
            //Test to make sure no customer stored message when arrayList is empty
            assertEquals(0, emptyCustomers!!.numberOfCustomers())
            assertTrue(emptyCustomers!!.listAllCustomers().lowercase().contains("no customer"))
        }

        @Test
        fun `listAllCustomers returns Customers`() {
            assertEquals(4,populatedCustomers!!.numberOfCustomers())
            val customerString = populatedCustomers!!.listAllCustomers().lowercase()
            println("\n--------------+"+ customerString + "+-------------------\n")
            assertTrue(customerString.contains("fred"))
            assertTrue(customerString.contains("joe"))
            assertTrue(customerString.contains("mary"))
            assertTrue(customerString.contains("mike"))
        }

        @Test
        fun listAllInstruments() {
            val noCustomer = emptyCustomers!!.listAllInstruments()
            assertTrue(noCustomer.contains("No customers"))

            assertEquals(4,populatedCustomers!!.numberOfCustomers())
            val customerInstrument = populatedCustomers!!.listAllInstruments()
            println("\n--------------+ $customerInstrument +-------------------\n")
            assertTrue(customerInstrument.contains("instrumentID"))
            assertTrue(customerInstrument.contains("Joe"))
            assertTrue(customerInstrument.contains("Drums"))
            assertTrue(customerInstrument.contains("Guitar"))

        }
    }

    @Nested
    inner class SearchMethods {
        @Test
        fun searchByNameTest() {
            assertEquals(4, populatedCustomers!!.numberOfCustomers())
            var searchResult = populatedCustomers!!.searchByName("no result expected")
            println("------------\n$searchResult\n-----------------")
            assertTrue(searchResult.isEmpty())

            assertEquals(0, emptyCustomers!!.numberOfCustomers())
            assertTrue(emptyCustomers!!.searchByName("").isEmpty())

            searchResult = populatedCustomers!!.searchByName("Joe")
            assertTrue(searchResult.contains("Joe"))
            assertFalse(searchResult.contains("Fred"))

            searchResult = populatedCustomers!!.searchByName("Fred")
            assertTrue(searchResult.contains("Fred"))
            assertFalse(searchResult.contains("Jay"))


            //Testing for customers with same Name
            val newCustomer = Customer(12, "Joe", "Galway", mutableSetOf(guitar), true, "Guitar")
            assertTrue(populatedCustomers!!.create(newCustomer))
            assertEquals(5, populatedCustomers!!.numberOfCustomers())
            searchResult = populatedCustomers!!.searchByName("Joe")
            println("------------\n$searchResult\n-----------------")
            assertTrue(searchResult.contains("Joe"))
        }

    }



}
