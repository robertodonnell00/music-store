package models

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CustomerTest {
    private var guitar: Instrument? = null//Instrument(0, "Guitar", 300.00, 1, true, 7, "03/02/22", 0)
    private var drums: Instrument? = null// Instrument(1, "Drums", 490.00, 1, true, 8, "31/10/22", 1)
    private var flute: Instrument? = null//Instrument(2, "Wind", 80.00, 1, true, 4, "15/04/21", 2)
    private var piano: Instrument? = null//Instrument(3, "Piano", 600.00, 1, true, 9, "28/06/23", 3)
    private var harp: Instrument? = null//Instrument(4, "Strings", 300.00, 1, true, 6, "09/08/23", 3)


    private var customerFred: Customer? = null
    private var customerJoe: Customer? = null
    //private var customerMary: Customer? =
    //private var customerMike: Customer? =

    private var populatedInstruments: MutableSet<Instrument> = mutableSetOf()
    private var emptyInstruments: MutableSet<Instrument> = mutableSetOf()

    @BeforeEach
    fun setup() {
        guitar = Instrument(0, "Guitar", 300.00, 1, true, 7, "03/02/22", 1)
        drums = Instrument(1, "Drums", 490.00, 1, true, 8, "31/10/22", 1)
        flute = Instrument(2, "Wind", 80.00, 1, true, 4, "15/04/21", 1)
        piano = Instrument(3, "Piano", 600.00, 1, false, 9, "28/06/23", 1)
        harp = Instrument(4, "Strings", 300.00, 1, true, 6, "09/08/23", 1)

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
            //Adding instrument to customer who owns other instruments
            var newInstrument = Instrument(8, "Wind", 420.30, 2, true, 8, "28/11/2023", 1)
            assertEquals(5, customerFred!!.itemsBought.size)
            assertTrue(customerFred!!.itemsBought.add(newInstrument))
            assertEquals(6, customerFred!!.itemsBought.size)
            assertEquals(newInstrument, customerFred!!.findInstrument(newInstrument.instrumentID))

            //Adding instrument to customer who owns no other instruments
            newInstrument = Instrument(8, "Wind", 420.30, 2, true, 8, "28/11/2023", 2)
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
            //Returns no instruments stored message when none are stored
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.listInstruments().lowercase().contains("no instruments"))

            //Returns instruments when Array list has instruments stored
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
            //Returns no instruments when none are stored
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.listInstrumentsPaidFor(true).lowercase().contains("no instruments"))

            //Returns only instruments that have been paid for
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
            //Returns no instruments when none are stored
            assertEquals(0, customerJoe!!.itemsBought.size)
            assertTrue(customerJoe!!.listInstrumentsByType("Guitar").lowercase().contains("no instruments"))

            assertTrue(customerFred!!.listInstrumentsByType("Guitar").contains("Guitar"))
            assertTrue(customerFred!!.listInstrumentsByType("Drums").contains("Drums"))
            assertTrue(customerFred!!.listInstrumentsByType("Piano").contains("Piano"))
            assertTrue(customerFred!!.listInstrumentsByType("Wind").contains("Wind"))

            //Testing for multiple of same type
            val newInstrument = Instrument(8, "Guitar", 420.30, 2, true, 8, "28/11/2023", 1)
            assertTrue(customerFred!!.itemsBought.add(newInstrument))
            assertTrue(customerFred!!.listInstrumentsByType("Guitar").contains("Guitar"))
            println(customerFred!!.listInstrumentsByType("Guitar"))
        }

    }

    @Nested
    inner class UpdateInstrument {

        fun updateInstrumentTest() {
            //Cannot update
        }

    }

}