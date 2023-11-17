import controllers.InstrumentAPI
import controllers.CustomerAPI
import models.Instrument
import models.Customer
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger{}

val scannerInput = ScannerInput

private val instrumentAPI = InstrumentAPI(JSONSerializer(File("instruments.json")))
private val customerAPI = CustomerAPI(JSONSerializer(File("customers.json")))
fun main(args: Array<String>) {
    runMainMenu()
}



    fun mainMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |         MUSIC    STORE         |
      > ----------------------------------
      > | Main Menu                      |
      > |    1) Customer Menu            |
      > |    2) Instrument Menu          |
      > ----------------------------------
      > |    3) Save changes            |
      > ----------------------------------
      > |    0) Exit                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runMainMenu() {
        load()
        do {
            when (val option = mainMenu()) {
                1 -> runCustomerMenu()
                2 -> runInstrumentMenu()
                // 3 -> save()
                // 0 -> exitApp()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

    fun customerMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer Menu:                 |
      > |    1) Customer List Menu       |
      > |    2) Customer Search Menu     |
      > |    3) Add a new Customer       |
      > |    4) Update a Customer        |
      > |    5) Delete a Customer        |
      > |    6) Calculate total paid by  |
      > |       Customer                 |
      > ----------------------------------
      > |    0) Main Menu                |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runCustomerMenu() {
        do {
            when (val option = customerMenu()) {
                1 -> runCustomerListMenu()
                2 -> runCustomerSearchMenu()
                // 3 -> addCustomer()
                // 4 -> updateCustomer()
                // 5 -> deleteCustomer()
                // 6 -> calculatePaid()
                0 -> runMainMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

    fun customerListMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer List Menu:            |
      > |    1) List all customers       |
      > |    2) List VIP customers       |
      > |    3) List Customers by        |
      > |       preferred instrument     |
      > |    4) List Customers who       | 
      > |       have not yet Paid        |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runCustomerListMenu() {
        do {
            when (val option = customerListMenu()) {
                // 1 -> listAllCustomers()
                // 2 -> listVIPCustomers()
                // 3 -> listCustomersByType()
                // 4 -> listCustomersByNotPaid()
                0 -> runCustomerMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

    fun customerSearchMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer Search Menu:          |
      > |    1) Search Customer by Name  |
      > |    2) Search Customer by ID    |
      > |    3) Search Customer by Item  |
      > |       Bought                   |
      > |    4) Search Customer by       |
      > |       Address                  |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runCustomerSearchMenu() {
        do {
            when (val option = customerSearchMenu()) {
                // 1 -> searchCustomerByName()
                // 2 -> searchCustomerByID()
                // 3 -> searchCustomerByItem()
                // 4 -> searchCustomerByAddress()
                0 -> runCustomerMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

    fun instrumentMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Instrument Menu:               |
      > |    1) List Menu                |
      > |    2) Search Menu              |
      > |    3) Add Instrument           |
      > |    4) Update Instrument        |
      > |    5) Delete Instrument        |
      > ----------------------------------
      > |    0) Main Menu                |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runInstrumentMenu() {
        do {
            when (val option = instrumentMenu()) {
                1 -> runInstrumentListMenu()
                2 -> runInstrumentSearchMenu()
                // 3 -> addInstrument()
                // 4 -> updateInstrument()
                // 5 -> deleteInstrument()
                0 -> runMainMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

    fun instrumentSearchMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Instrument Search Menu:        |
      > |    1) Search Instrument by ID  |
      > |    2) Search Instrument by     |
      > |       Price                    |
      > |    3) Search Instrument by     |
      > |       Date Received            |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runInstrumentSearchMenu() {
        do {
            when (val option = instrumentSearchMenu()) {
                // 1 -> searchInstrumentByID()
                // 2 -> searchInstrumentByPrice()
                // 3 -> searchInstrumentByDateReceived()
                0 -> runInstrumentMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

    fun instrumentListMenu(): Int {
        return scannerInput.readNextInt(
            """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Instrument List Menu:          |
      > |    1) List all instruments     |
      > |    2) List instruments of type |
      > |    3) List instruments by      |
      > |       customer who bought      |
      > |    4) List instruments by      |
      > |       Review                   |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
        )
    }

    fun runInstrumentListMenu() {
        do {
            when (val option = instrumentListMenu()) {
                // 1 -> listAllInstruments()
                // 2 -> listInstrumentsOfType()
                // 3 -> listInstrumentsByCustomer()
                // 4 -> listInstrumentsByReview()
                0 -> runInstrumentMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

fun exitApp(){
    save()
    logger.info { "exitApp() function invoked" }
    exit(0)
}

fun save() {

    try {
        customerAPI.store()
        instrumentAPI.store()
    } catch (e: Exception){
        System.err.println("Error writing to file: $e")
    }
    logger.info { "Changes saved" }
}

fun load() {
    try {
        customerAPI.load()
        instrumentAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}



//CRUD
//CREATE

fun addCustomer() {
    val customerID = readNextLine("Enter Customer ID: ")
    val customerName = readNextLine("Enter Customer Name: ")
    val customerAddress = readNextLine("Enter Customer Address: ")
    val itemsBought = readNextLine("Enter ID of items bought: ")
    println("Enter Customer VIP Status (true/false): ")
    val vipCustomer = readLine()!!.toBoolean()
    val preferredInstrument = readNextLine("Enter preferred type of Instrument: ")

    val isAdded = customerAPI.create(Customer(customerID,customerName,customerAddress,itemsBought,vipCustomer, preferredInstrument))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun addInstrument() {
    val instrumentID = readNextLine("Enter Instrument ID: ")
    val instrumentType = readNextLine("Enter the Type of Instrument: ")
    val price = readNextDouble("Enter the price of the instrument: ")
    val quantityBought = readNextInt("Enter the quantity of instruments the customer bought: ")
    println("Did the customer pay for instrument: ")
    val isPaidFor = readLine()!!.toBoolean()
    val instrumentReview = readNextInt("Enter the review for instrument: ")
    val dateReceived = readNextLine("Enter the date the customer received instrument: ")
    val customerBought = readNextLine("Enter the ID of the customer who bought this instrument: ")

    val isAdded = instrumentAPI.add(Instrument(instrumentID,instrumentType,price,quantityBought, isPaidFor,instrumentReview,dateReceived,customerBought))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}