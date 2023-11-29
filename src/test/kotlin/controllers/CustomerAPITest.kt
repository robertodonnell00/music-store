package controllers

import models.Customer
import models.Instrument
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
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

    private var guitar: Instrument = Instrument(0, "Stratocaster","Guitar", 300.00, 1, true, 7, "03/02/22", 0)
    private var drums: Instrument = Instrument(1, "Snare","Drums", 490.00, 1, true, 8, "31/10/22", 1)
    private var flute: Instrument = Instrument(2, "Jazz Flute","Wind", 80.00, 1, true, 4, "15/04/21", 2)
    private var piano: Instrument = Instrument(3, "E-Piano","Piano", 600.00, 1, true, 9, "28/06/23", 3)
    private var harp: Instrument = Instrument(4, "Harp","Strings", 300.00, 1, true, 6, "09/08/23", 3)

    @BeforeEach
    fun setup() {
        customerFred = Customer(1, "Fred", "Waterford", mutableSetOf(guitar), true, "Guitar")
        customerJoe = Customer(2, "Joe", "Kilkenny", mutableSetOf(drums), true, "Drums")
        customerMary = Customer(3, "Mary", "Waterford", mutableSetOf(flute), true, "Wind")
        customerMike = Customer(4, "Mike", "Wexford", mutableSetOf(piano, harp), false, "Piano")

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

        @Test
        fun listVIPCustomersTest() {
            val noCustomer = emptyCustomers!!.listVIPCustomers()
            assertTrue(noCustomer.contains("No customers"))

            assertEquals(4,populatedCustomers!!.numberOfCustomers())
            val VIPcustomers = populatedCustomers!!.listVIPCustomers()
            println("------\n$VIPcustomers\n--------")
            assertTrue(VIPcustomers.contains("Fred"))
            assertTrue(VIPcustomers.contains("Joe"))
            assertTrue(VIPcustomers.contains("Mary"))
            assertFalse(VIPcustomers.contains("Mike"))

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

        @Test
        fun searchByAddressTest(){
            assertEquals(4, populatedCustomers!!.numberOfCustomers())
            var searchResult = populatedCustomers!!.searchByAddress("no result expected")
            println("------------\n$searchResult\n-----------------")
            assertTrue(searchResult.isEmpty())

            assertEquals(0, emptyCustomers!!.numberOfCustomers())
            assertTrue(emptyCustomers!!.searchByAddress("").isEmpty())

            searchResult = populatedCustomers!!.searchByAddress("Waterford")
            assertTrue(searchResult.contains("Waterford"))
            assertFalse(searchResult.contains("Kilkenny"))

            searchResult = populatedCustomers!!.searchByAddress("Kilkenny")
            assertTrue(searchResult.contains("Kilkenny"))
            assertFalse(searchResult.contains("Waterford"))
        }

        @Test
        fun searchByIDTest() {
            assertEquals(4, populatedCustomers!!.numberOfCustomers())
            var searchResult = populatedCustomers!!.searchByID(0)
           //println("------------\n$searchResult\n-----------------")
            assertTrue(searchResult.isEmpty())

            searchResult = populatedCustomers!!.searchByID(1)
            assertTrue(searchResult.contains("customerID='1'"))
            assertFalse(searchResult.contains("customerID='2'"))
            println("------------\n$searchResult\n-----------------")

            searchResult = populatedCustomers!!.searchByID(3)
            assertTrue(searchResult.contains("customerID='3'"))
            assertTrue(!searchResult.contains("customerID='2'"))
        }

        @Test
        fun searchByItemsBoughtTest() {
            //Searching for an instrument not assigned to anyone will return nothing
            val keys: Instrument = Instrument(293,"Fender", "Strings", 300.00, 1, true, 6, "09/08/23", 34)
            assertEquals(4, populatedCustomers!!.numberOfCustomers())
            var searchResult = populatedCustomers!!.searchByItemBought(mutableSetOf(keys))
            assertTrue(searchResult.isEmpty())

            //Searching for an instrument will return the customer who owns it
            searchResult = populatedCustomers!!.searchByItemBought(mutableSetOf(guitar))
            assertTrue(searchResult.contains("Fred"))
            assertTrue(!searchResult.contains("Joe"))

            searchResult = populatedCustomers!!.searchByItemBought(mutableSetOf(drums))
            assertTrue(searchResult.contains("Joe"))
            assertTrue(!searchResult.contains("Fred"))

            //Testing for customer who owns mutliple instruments
            searchResult = populatedCustomers!!.searchByItemBought(mutableSetOf(piano, harp))
            assertTrue(searchResult.contains("Mike"))
            assertTrue(!searchResult.contains("Mary"))
            println("-----------\n$searchResult\n-------------")
        }

    }

    @Nested
    inner class DeleteCustomer {
        @Test
        fun deleteTest() {
            //deleting a customer that doesn't exist returns null
            assertNull(emptyCustomers!!.deleteCustomer(0))
            assertNull(populatedCustomers!!.deleteCustomer(-1))
            assertNull(populatedCustomers!!.deleteCustomer(8))

            //Fred0, Joe1, Mary2, Mike3
            assertEquals(4, populatedCustomers!!.numberOfCustomers())
            assertEquals(customerMike, populatedCustomers!!.deleteCustomer(3))
            assertEquals(3, populatedCustomers!!.numberOfCustomers())
            assertEquals(customerFred, populatedCustomers!!.deleteCustomer(0))
            assertEquals(2, populatedCustomers!!.numberOfCustomers())
        }
    }


    @Nested
    inner class UpdateCustomer {

        @Test
        fun updateCustomerTest() {
            //Cannot update customer that doesn't exist
            assertFalse(populatedCustomers!!.updateCustomer(7, Customer(5,"James", "Galway", mutableSetOf(guitar),true,"Guitar")))
            assertFalse(populatedCustomers!!.updateCustomer(-1, Customer(5,"James", "Galway", mutableSetOf(guitar),true,"Guitar")))
            assertFalse(emptyCustomers!!.updateCustomer(0, Customer(5,"James", "Galway", mutableSetOf(guitar),true,"Guitar")))

            //Checking customer exists and its properties are correct
            assertEquals(customerFred, populatedCustomers!!.findCustomer(0))
            assertEquals("Fred", populatedCustomers!!.findCustomer(0)!!.customerName)
            assertEquals("Waterford", populatedCustomers!!.findCustomer(0)!!.customerAddress)
            assertEquals(1, populatedCustomers!!.findCustomer(0)!!.customerID)

            //Update customerFred with new info and ensure update successful
            assertTrue(populatedCustomers!!.updateCustomer(0, Customer(9,"Tester","Wicklow", customerFred!!.itemsBought,true, "Drums")))
            println("---------\n$customerFred\n------------")
            assertEquals("Tester", populatedCustomers!!.findCustomer(0)!!.customerName)
            assertEquals("Wicklow", populatedCustomers!!.findCustomer(0)!!.customerAddress)
            assertEquals(9, populatedCustomers!!.findCustomer(0)!!.customerID)
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            val storingCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            storingCustomers.store()

            val loadedCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            loadedCustomers.load()

            assertEquals(0,storingCustomers.numberOfCustomers())
            assertEquals(0,loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.numberOfCustomers(), loadedCustomers.numberOfCustomers())
        }
        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            val storingCustomers = CustomerAPI(JSONSerializer(File("customers.json")))
            storingCustomers.store()

            val loadedCustomers = CustomerAPI(JSONSerializer(File("customers.json")))
            loadedCustomers.load()

            assertEquals(0,storingCustomers.numberOfCustomers())
            assertEquals(0,loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.numberOfCustomers(), loadedCustomers.numberOfCustomers())
        }


        @Test
        fun `saving and loading Customers in XML doesn't loose data`() {
            //Store 3 notes
            val storingCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            storingCustomers.create(customerMike!!)
            storingCustomers.create(customerJoe!!)
            storingCustomers.create(customerMary!!)
            storingCustomers.store()

            //loading customers.xml into a different collection
            val loadedCustomers = CustomerAPI(XMLSerializer(File("customers.xml")))
            loadedCustomers.load()

            assertEquals(3,storingCustomers.numberOfCustomers())
            assertEquals(3,loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.numberOfCustomers(), loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.findCustomer(0)!!.customerName, loadedCustomers.findCustomer(0)!!.customerName)
            assertEquals(storingCustomers.findCustomer(1)!!.customerName, loadedCustomers.findCustomer(1)!!.customerName)
            assertEquals(storingCustomers.findCustomer(2)!!.customerName, loadedCustomers.findCustomer(2)!!.customerName)
        }
        @Test
        fun `saving and loading Customers in JSON doesn't loose data`() {
            //Store 3 notes
            val storingCustomers = CustomerAPI(XMLSerializer(File("customers.json")))
            storingCustomers.create(customerMike!!)
            storingCustomers.create(customerJoe!!)
            storingCustomers.create(customerMary!!)
            storingCustomers.store()

            //loading customers.xml into a different collection
            val loadedCustomers = CustomerAPI(XMLSerializer(File("customers.json")))
            loadedCustomers.load()

            assertEquals(3,storingCustomers.numberOfCustomers())
            assertEquals(3,loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.numberOfCustomers(), loadedCustomers.numberOfCustomers())
            assertEquals(storingCustomers.findCustomer(0)!!.customerName, loadedCustomers.findCustomer(0)!!.customerName)
            assertEquals(storingCustomers.findCustomer(1)!!.customerName, loadedCustomers.findCustomer(1)!!.customerName)
            assertEquals(storingCustomers.findCustomer(2)!!.customerName, loadedCustomers.findCustomer(2)!!.customerName)
        }
    }



}
