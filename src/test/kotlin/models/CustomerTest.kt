package models

import org.junit.jupiter.api.BeforeEach

class CustomerTest {
    private var guitar: Instrument? = null//Instrument(0, "Guitar", 300.00, 1, true, 7, "03/02/22", 0)
    private var drums: Instrument? = null// Instrument(1, "Drums", 490.00, 1, true, 8, "31/10/22", 1)
    private var flute: Instrument? = null//Instrument(2, "Wind", 80.00, 1, true, 4, "15/04/21", 2)
    private var piano: Instrument? = null//Instrument(3, "Piano", 600.00, 1, true, 9, "28/06/23", 3)
    private var harp: Instrument? = null//Instrument(4, "Strings", 300.00, 1, true, 6, "09/08/23", 3)

    //private var customerFred: Customer? = Customer(1, "Fred", "Waterford", mutableSetOf(guitar), true, "Guitar")
    //private var customerJoe: Customer? =
    //private var customerMary: Customer? =
    //private var customerMike: Customer? =
    //private var populatedInstruments: Instrument? = Customer()
    //private var emptyInstruments: Instrument? = Customer()

    @BeforeEach
    fun setup() {
        guitar = Instrument(0, "Guitar", 300.00, 1, true, 7, "03/02/22", 0)
        drums = Instrument(1, "Drums", 490.00, 1, true, 8, "31/10/22", 1)
        flute = Instrument(2, "Wind", 80.00, 1, true, 4, "15/04/21", 2)
        piano = Instrument(3, "Piano", 600.00, 1, true, 9, "28/06/23", 3)
        harp = Instrument(4, "Strings", 300.00, 1, true, 6, "09/08/23", 3)

    }
}