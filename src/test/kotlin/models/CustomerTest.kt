package models

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CustomerTest {
    private var guitar: Instrument? = null // Instrument(0, "Guitar", 300.00, 1, true, 7, "03/02/22", 0)
    private var drums: Instrument? = null // Instrument(1, "Drums", 490.00, 1, true, 8, "31/10/22", 1)
    private var flute: Instrument? = null // Instrument(2, "Wind", 80.00, 1, true, 4, "15/04/21", 2)
    private var piano: Instrument? = null // Instrument(3, "Piano", 600.00, 1, true, 9, "28/06/23", 3)
    private var harp: Instrument? = null // Instrument(4, "Strings", 300.00, 1, true, 6, "09/08/23", 3)

    private var customerFred: Customer? = null
    private var customerJoe: Customer? = null
    // private var customerMary: Customer? =
    // private var customerMike: Customer? =

    private var populatedInstruments: MutableSet<Instrument> = mutableSetOf()
    private var emptyInstruments: MutableSet<Instrument> = mutableSetOf()

    @BeforeEach
    fun setup() {
        guitar = Instrument(0, "Stratocaster", "Guitar", 300.00, 1, true, 7, "03/02/22", 1)
        drums = Instrument(1, "Snare", "Drums", 490.00, 3, true, 8, "31/10/22", 1)
        flute = Instrument(2, "Jazz Flute", "Wind", 80.00, 1, true, 4, "15/04/21", 1)
        piano = Instrument(3, "Electric Keys", "Piano", 600.00, 1, false, 9, "28/06/23", 1)
        harp = Instrument(4, "Harp", "Strings", 300.00, 1, true, 6, "09/08/23", 1)

        populatedInstruments.add(guitar!!)
        populatedInstruments.add(drums!!)
        populatedInstruments.add(flute!!)
        populatedInstruments.add(piano!!)
        populatedInstruments.add(harp!!)

        customerFred = Customer(1, "Fred", "Waterford", populatedInstruments, true, "Guitar")
        customerJoe = Customer(2, "Joe", "Kilkenny", emptyInstruments, false, "Drums")
    }

    @AfterEach
    fun tearDown() {
        guitar = null
        drums = null
        flute = null
        piano = null
        harp = null
        populatedInstruments.clear()
        emptyInstruments.clear()

        customerFred = null
        customerJoe = null
    }

    @Nested
    inner class AddInstruments {
        @Test
        fun createTest() {
            // Adding instrument to customer who owns other instruments
            var newInstrument = Instrument(8, "Saxophone", "Wind", 420.30, 2, true, 8, "28/11/2023", 1)
            assertEquals(5, customerFred!!.itemsBought.size)
            assertTrue(customerFred!!.itemsBought.add(newInstrument))
            assertEquals(6, customerFred!!.itemsBought.size)
            assertEquals(newInstrument, customerFred!!.findInstrument(newInstrument.instrumentID))

            // Adding instrument to customer who owns no other instruments
            newInstrument = Instrument(8, "Saxophone", "Wind", 420.30, 2, true, 8, "28/11/2023", 2)
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.itemsBought.add(newInstrument))
            assertEquals(1, customerJoe!!.itemsBought.size)
            assertEquals(newInstrument, customerJoe!!.findInstrument(newInstrument.instrumentID))
        }
    }

    @Nested
    inner class ListInstruments {
        @Test
        fun listInstrumentsTest() {
            // Returns no instruments stored message when none are stored
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.listInstruments().lowercase().contains("no instruments"))

            // Returns instruments when Array list has instruments stored
            assertEquals(5, customerFred!!.itemsBought.size)
            val instrumentString = customerFred!!.listInstruments().lowercase()
            assertTrue(instrumentString.contains("guitar"))
            assertTrue(instrumentString.contains("drums"))
            assertTrue(instrumentString.contains("wind"))
            assertTrue(instrumentString.contains("piano"))
            assertTrue(instrumentString.contains("strings"))
        }

        @Test
        fun listInstrumentsPaidForTest() {
            // Returns no instruments when none are stored
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.listInstrumentsPaidFor(true).lowercase().contains("no instruments"))

            // Returns only instruments that have been paid for
            assertEquals(5, customerFred!!.itemsBought.size)
            var instrumentPaid = customerFred!!.listInstrumentsPaidFor(true)
            println("------------\n$customerFred\n-------------")
            assertTrue(customerFred!!.listInstrumentsPaidFor(true).contains("Guitar"))
            assertTrue(customerFred!!.listInstrumentsPaidFor(true).contains("Drums"))
            assertTrue(customerFred!!.listInstrumentsPaidFor(true).contains("Strings"))
            assertTrue(customerFred!!.listInstrumentsPaidFor(true).contains("Wind"))
            assertTrue(customerFred!!.listInstrumentsPaidFor(false).contains("Piano"))
        }

        @Test
        fun listInstrumentsByTypeTest() {
            // Returns no instruments when none are stored
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.listInstrumentsByType("Guitar").lowercase().contains("no instruments"))

            assertTrue(customerFred!!.listInstrumentsByType("Guitar").contains("Guitar"))
            assertTrue(customerFred!!.listInstrumentsByType("Drums").contains("Drums"))
            assertTrue(customerFred!!.listInstrumentsByType("Piano").contains("Piano"))
            assertTrue(customerFred!!.listInstrumentsByType("Wind").contains("Wind"))

            // Testing for multiple of same type
            val newInstrument = Instrument(8, "Fender", "Guitar", 420.30, 2, true, 8, "28/11/2023", 1)
            assertTrue(customerFred!!.itemsBought.add(newInstrument))
            assertTrue(customerFred!!.listInstrumentsByType("Guitar").contains("Guitar"))
            println(customerFred!!.listInstrumentsByType("Guitar"))
        }
    }

    @Nested
    inner class SearchInstrument {

        @Test
        fun searchByInstrumentIDTest() {
            // Search returns no instruments when no instrument of given ID exist
            assertEquals(5, customerFred!!.numberOfInstruments())
            var searchResult = customerFred!!.searchInstrumentByID(99)
            assertTrue(searchResult.isEmpty())

            // Searching an empty collection
            assertEquals(0, customerJoe!!.numberOfInstruments())
            println(customerJoe!!.searchInstrumentByID(99))
            assertTrue(customerJoe!!.searchInstrumentByID(99).contains("No instruments"))

            // Searching a populated collection for an id returns that instrument
            searchResult = customerFred!!.searchInstrumentByID(2)
            assertTrue(searchResult.contains("Wind"))
            assertFalse(searchResult.contains("Guitar"))
            println("---------\n$searchResult\n---------")
        }

        @Test
        fun searchByQuantityBoughtTest() {
            // Search returns no instruments when no instrument of given ID exist
            assertEquals(5, customerFred!!.numberOfInstruments())
            var searchResult = customerFred!!.searchByQuantityBought(7)
            assertTrue(searchResult.isEmpty())

            // Searching an empty collection
            assertEquals(0, customerJoe!!.numberOfInstruments())
            assertTrue(customerJoe!!.searchByQuantityBought(7).contains("No instruments"))

            // Searching a populated collection for quantity returns that instrument
            searchResult = customerFred!!.searchByQuantityBought(3)
            assertTrue(searchResult.contains("Drums"))
            assertFalse(searchResult.contains("Guitar"))
            println("---------\n$searchResult\n---------")
        }

        @Test
        fun searchByDateReceivedTest() {
            // Search returns no instruments when no instrument of given date exist
            assertEquals(5, customerFred!!.numberOfInstruments())
            var searchResult = customerFred!!.searchByDateReceived("No insrument")
            assertTrue(searchResult.isEmpty())

            // Searching an empty collection
            assertEquals(0, customerJoe!!.numberOfInstruments())
            assertTrue(customerJoe!!.searchByDateReceived("No insrument").contains("No instruments"))

            // Searching a populated collection for a date returns instrument
            searchResult = customerFred!!.searchByDateReceived("03/02/22")
            assertTrue(searchResult.contains("Guitar"))
            assertFalse(searchResult.contains("Drums"))

            // Searching for instruments bought on same day
            val newInstrument = Instrument(8, "Banjo", "Strings", 420.30, 2, true, 8, "03/02/22", 1)
            assertTrue(customerFred!!.itemsBought.add(newInstrument))
            searchResult = customerFred!!.searchByDateReceived("03/02/22")
            assertTrue(searchResult.contains("Guitar"))
            assertTrue(searchResult.contains("Banjo"))
            assertFalse(searchResult.contains("Drums"))
            println("---------\n$searchResult\n---------")
        }
    }

    @Test
    fun searchByDateReceivedTest() {
        // Search returns no instruments when no instrument of given ID exist
        assertEquals(5, customerFred!!.numberOfInstruments())
        var searchResult = customerFred!!.searchByDateReceived("No insrument")
        assertTrue(searchResult.isEmpty())

        // Searching an empty collection
        assertEquals(0, customerJoe!!.numberOfInstruments())
        assertTrue(customerJoe!!.searchByDateReceived("No instrument").contains("No instruments"))

        // Searching a populated collection for a date returns instrument bought on given day
        searchResult = customerFred!!.searchByDateReceived("03/02/22")
        assertTrue(searchResult.contains("Guitar"))
        assertFalse(searchResult.contains("Drums"))

        // Searching for instruments bought on same day
        val newInstrument = Instrument(8, "Banjo", "Strings", 420.30, 2, true, 8, "03/02/22", 1)
        assertTrue(customerFred!!.itemsBought.add(newInstrument))
        searchResult = customerFred!!.searchByDateReceived("03/02/22")
        assertTrue(searchResult.contains("Guitar"))
        assertTrue(searchResult.contains("Banjo"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")
    }

    @Test
    fun searchByInstrumentNameTest() {
        // Search returns no instruments when no instrument of given ID exist
        assertEquals(5, customerFred!!.numberOfInstruments())
        var searchResult = customerFred!!.searchByInstrumentName("No instrument")
        assertTrue(searchResult.isEmpty())

        // Searching an empty collection
        assertEquals(0, customerJoe!!.numberOfInstruments())
        assertTrue(customerJoe!!.searchByInstrumentName("No instrument").contains("No instruments"))

        // Searching a populated collection for a name returns instrument
        searchResult = customerFred!!.searchByInstrumentName("Stratocaster")
        assertTrue(searchResult.contains("Guitar"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")

        // Searching for partial name
        searchResult = customerFred!!.searchByInstrumentName("Strat")
        assertTrue(searchResult.contains("Guitar"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")
    }

    @Test
    fun searchByReviewTest() {
        // Search returns no instruments when no instrument of given ID exist
        assertEquals(5, customerFred!!.numberOfInstruments())
        var searchResult = customerFred!!.searchByReview(55)
        assertTrue(searchResult.isEmpty())

        // Searching an empty collection
        assertEquals(0, customerJoe!!.numberOfInstruments())
        assertTrue(customerJoe!!.searchByReview(34).contains("No instruments"))

        // Searching a populated collection for a review returns that instrument
        searchResult = customerFred!!.searchByReview(7)
        assertTrue(searchResult.contains("Guitar"))
        assertFalse(searchResult.contains("Drums"))

        // Searching for instruments bought on same day
        val newInstrument = Instrument(8, "Banjo", "Strings", 420.30, 2, true, 7, "03/02/22", 1)
        assertTrue(customerFred!!.itemsBought.add(newInstrument))
        searchResult = customerFred!!.searchByReview(7)
        assertTrue(searchResult.contains("Guitar"))
        assertTrue(searchResult.contains("Banjo"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")
    }

    @Test
    fun searchByPriceTest() {
        // Search returns no instruments when no instrument of given price exist
        assertEquals(5, customerFred!!.numberOfInstruments())
        var searchResult = customerFred!!.searchByPrice(4254.34)
        assertTrue(searchResult.isEmpty())

        // Searching an empty collection
        assertEquals(0, customerJoe!!.numberOfInstruments())
        assertTrue(customerJoe!!.searchByPrice(34324.2).contains("No instruments"))

        // Searching a populated collection for a review returns that instrument
        searchResult = customerFred!!.searchByPrice(300.00)
        assertTrue(searchResult.contains("Guitar"))
        assertFalse(searchResult.contains("Drums"))

        // Searching for instruments of same price
        var newInstrument = Instrument(8, "Banjo", "Strings", 300.00, 2, true, 7, "03/02/22", 1)
        assertTrue(customerFred!!.itemsBought.add(newInstrument))
        searchResult = customerFred!!.searchByPrice(300.00)
        assertTrue(searchResult.contains("Guitar"))
        assertTrue(searchResult.contains("Banjo"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")

        // Searching for instrument over/under price by 10
        newInstrument = Instrument(8, "Bass", "Strings", 305.00, 2, true, 7, "03/02/22", 1)
        assertTrue(customerFred!!.itemsBought.add(newInstrument))
        searchResult = customerFred!!.searchByPrice(300.00)
        assertTrue(searchResult.contains("Guitar"))
        assertTrue(searchResult.contains("Bass"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")

        newInstrument = Instrument(8, "Bass", "Strings", 291.80, 2, true, 7, "03/02/22", 1)
        assertTrue(customerFred!!.itemsBought.add(newInstrument))
        searchResult = customerFred!!.searchByPrice(300.00)
        assertTrue(searchResult.contains("Guitar"))
        assertTrue(searchResult.contains("Bass"))
        assertFalse(searchResult.contains("Drums"))
        println("---------\n$searchResult\n---------")
    }

    @Nested
    inner class UpdateInstrument {
        @Test
        fun updateInstrumentTest() {
            // Cant update instrument that doesnt exist
            assertFalse(customerFred!!.updateInstrument(8, Instrument(8, "Bass", "Strings", 291.80, 2, true, 7, "03/02/22", 1)))
            assertFalse(customerFred!!.updateInstrument(-1, Instrument(8, "Bass", "Strings", 291.80, 2, true, 7, "03/02/22", 1)))
            assertFalse(customerJoe!!.updateInstrument(0, Instrument(0, "Bass", "Strings", 291.80, 2, true, 7, "03/02/22", 1)))

            assertEquals(guitar, customerFred!!.findInstrument(0))
            // assertEquals(guitar, customerFred!!.updateInstrument(0, Instrument(0, "Stratocaster", "Guitar", 300.00, 1, true, 7, "03/02/22", 1)))
            assertEquals("Guitar", customerFred!!.findInstrument(0)!!.instrumentType)

            assertTrue(customerFred!!.updateInstrument(0, Instrument(0, "Update", "String", 350.00, 1, false, 7, "03/02/22", 1)))
            assertEquals("Update", customerFred!!.findInstrument(0)!!.instrumentName)
            assertEquals("String", customerFred!!.findInstrument(0)!!.instrumentType)
            assertEquals(350.00, customerFred!!.findInstrument(0)!!.price)
            assertEquals(false, customerFred!!.findInstrument(0)!!.isPaidFor)
        }
    }

    @Nested
    inner class DeleteInstrument {
        @Test
        fun deleteInstrumentTest() {
            // cant delete instrument that doesnt exist
            assertFalse(customerJoe!!.delete(0))
            assertFalse(customerFred!!.delete(-1))
            assertFalse(customerFred!!.delete(9))

            assertEquals(5, customerFred!!.numberOfInstruments())
            assertTrue(customerFred!!.delete(0))
            assertEquals(4, customerFred!!.numberOfInstruments())
            assertTrue(customerFred!!.delete(1))
            assertEquals(3, customerFred!!.numberOfInstruments())
        }
    }
}
