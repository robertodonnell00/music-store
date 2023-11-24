import controllers.CustomerAPI
import models.Instrument
import models.Customer
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger{}

val scannerInput = ScannerInput

//private val instrumentAPI = InstrumentAPI(JSONSerializer(File("instruments.json")))
private val customerAPI = CustomerAPI(JSONSerializer(File("customers.json")))
fun main(args: Array<String>) {
    dummyData()
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
                0 -> exitApp()
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
                3 -> addCustomer()
                4 -> updateCustomer()
                5 -> deleteCustomer()
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
                 1 -> listAllCustomers()
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
      > |    1) Add Instrument           |
      > |    2) Update Instrument        |
      > |    3) Delete Instrument        |
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
                1 -> addInstrumentToCustomer()
                2 -> updateInstrument()
                3 -> deleteInstrument()
                0 -> runMainMenu()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }

//    fun instrumentSearchMenu(): Int {
//        return scannerInput.readNextInt(
//            """
//      > ----------------------------------
//      > |          MUSIC  STORE          |
//      > ----------------------------------
//      > | Instrument Search Menu:        |
//      > |    1) Search Instrument by ID  |
//      > |    2) Search Instrument by     |
//      > |       Price                    |
//      > |    3) Search Instrument by     |
//      > |       Date Received            |
//      > ----------------------------------
//      > |    0) Back                     |
//      > ----------------------------------
//      > ==>>
//    """.trimMargin(">")
//        )
//    }

//    fun runInstrumentSearchMenu() {
//        do {
//            when (val option = instrumentSearchMenu()) {
//                // 1 -> searchInstrumentByID()
//                // 2 -> searchInstrumentByPrice()
//                // 3 -> searchInstrumentByDateReceived()
//                0 -> runInstrumentMenu()
//                else -> println("Invalid option selected: $option")
//            }
//        } while (true)
//    }

//    fun instrumentListMenu(): Int {
//        return scannerInput.readNextInt(
//            """
//      > ----------------------------------
//      > |          MUSIC  STORE          |
//      > ----------------------------------
//      > | Instrument List Menu:          |
//      > |    1) List all instruments     |
//      > |    2) List instruments of type |
//      > |    3) List instruments by      |
//      > |       customer who bought      |
//      > |    4) List instruments by      |
//      > |       Review                   |
//      > ----------------------------------
//      > |    0) Back                     |
//      > ----------------------------------
//      > ==>>
//    """.trimMargin(">")
//        )
//    }

//    fun runInstrumentListMenu() {
//        do {
//            when (val option = instrumentListMenu()) {
//                 1 -> listAllCustomerItems()
//                // 2 -> listInstrumentsOfType()
//                // 3 -> listInstrumentsByCustomer()
//                // 4 -> listInstrumentsByReview()
//                0 -> runInstrumentMenu()
//                else -> println("Invalid option selected: $option")
//            }
//        } while (true)
//    }


//PERSISTENCE

fun exitApp(){
   // save()
    logger.info { "exitApp() function invoked" }
    exit(0)
}

fun save() {

    try {
        customerAPI.store()

    } catch (e: Exception){
        System.err.println("Error writing to file: $e")
    }
    logger.info { "Changes saved" }
}

fun load() {
    try {
        customerAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}



//CRUD
//CREATE

fun addCustomer() {
    val customerID = readNextInt("Enter Customer ID: ")
    val customerName = readNextLine("Enter Customer Name: ")
    val customerAddress = readNextLine("Enter Customer Address: ")
    println("Enter Customer VIP Status (true/false): ")
    val vipCustomer = readln().toBoolean()
    val preferredInstrument = readNextLine("Enter preferred type of Instrument: ")

    val isAdded = customerAPI.create(Customer(customerID = customerID,
        customerName= customerName, customerAddress= customerAddress,
        vipCustomer= vipCustomer, preferredInstrument = preferredInstrument))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

private fun chooseCustomer(): Customer? {
    listAllCustomers()
    if (customerAPI.numberOfCustomers() > 0){
        val customer = customerAPI.findCustomer(readNextInt("\nEnter id of the customer: "))
        if (customer != null) return customer else {println("Customer ID invalid")}
    }
    return null
}

private fun chooseInstrument(customer: Customer): Instrument? {
    if(customerAPI.numberOfCustomers() > 0) {
        println(customer.listInstruments())
        return customer.findInstrument(readNextInt("\nEnter ID of the Instrument: "))
    }
    else {
        println("No instruments for chosen Customer")
        return null
    }
}


fun addInstrumentToCustomer() {
    val customer: Customer? = chooseCustomer()

    if(customer != null){
        val instrumentID = readNextInt("Enter Instrument ID: ")
        val instrumentType = readNextLine("Enter the Type of Instrument: ")
        val price = readNextDouble("Enter the price of the instrument: ")
        val quantityBought = readNextInt("Enter the quantity of instruments the customer bought: ")
        println("Did the customer pay for instrument: ")
        val isPaidFor = readln().toBoolean()
        val instrumentReview = readNextInt("Enter the review for instrument: ")
        val dateReceived = readNextLine("Enter the date the customer received instrument: ")
        listAllCustomers()
        val customerIndex = readNextInt("Enter the index of the customer who bought this instrument: ")
        val customerBoughtID = readNextInt("Enter the id of customer who bought this instrument")

        val isAdded = customer.create(Instrument(instrumentID,instrumentType,price,quantityBought, isPaidFor,instrumentReview,dateReceived, customerBoughtID))
        if (isAdded) {
            println("Added Successfully")
        } else {
            println("Add Failed")
        }

    }

}

//READ

fun listAllCustomers() {
    println(customerAPI.listAllCustomers())
}

fun listAllCustomerItems() {
    println(customerAPI.listAllInstruments())
}





//UPDATE
fun updateCustomer() {
    listAllCustomers()
    if(customerAPI.numberOfCustomers() > 0) {
        //only ask the user to choose the customer if it exists
        val indexToUpdate = readNextInt("Enter the index of the Customer you wish to update: ")
        if(customerAPI.isValidIndex(indexToUpdate)){
            val customerID = readNextInt("Enter customer ID: ")
            val customerName = readNextLine("Enter customer name: ")
            val customerAddress = readNextLine("Enter customer address: ")
            //val instrumentIndex = readNextInt("Enter index of instrument: ")
            //val itemsBought = instrumentAPI.getInstrument(instrumentIndex)
            println("Is customer VIP? (true/false)")
            val customerVIP = readln().toBoolean()
            val preferredInstrument = readNextLine("Enter the customer's preferred Instrument type: ")
            if (customerAPI.updateCustomer(indexToUpdate, Customer(customerID,customerName,customerAddress, mutableSetOf(), customerVIP,preferredInstrument))){
                println("Update Successful")
            } else {
                println("Update Failed")
                }
            } else {
                println("There are no notes of that index number")
            }
        }
    }

fun updateInstrument() {
    val customer: Customer? = chooseCustomer()
    if (customer != null) {
        val instrument: Instrument? = chooseInstrument(customer)

        if (instrument != null){
            val instrumentID = readNextInt("Enter the instrument ID: ")
            val instrumentType = readNextLine("Enter the instrument type: ")
            val price = readNextDouble("Enter the price of instrument: ")
            val quantityBought = readNextInt("Enter the quantity ordered: ")
            println("Has the instrument been paid for? ")
            val isPaidFor = readln().toBoolean()
            val instrumentReview = readNextInt("Enter review out of 100: ")
            val dateReceived = readNextLine("Enter date the customer received instrument: ")

            if(customer.updateInstrument(instrument.instrumentID, Instrument(
                        instrumentID, instrumentType, price, quantityBought,
                        isPaidFor, instrumentReview, dateReceived, customer.customerID
                )))
                    println("Instrument details updated")
                    else println("Invalid Instrument ID")
        }
    }

}



//DELETE

fun deleteCustomer(){
    listAllCustomers()
    if(customerAPI.numberOfCustomers() > 0) {
        //only ask user to choose customer to be deleted if it exists
        val indexToDelete = readNextInt("Enter the index of the customer you wish to delete: ")
        //pass index of customer to controller for deleting and check for success
        val customerToDelete = customerAPI.deleteCustomer(indexToDelete)

        println("Delete Successful! Customer deleted : ${customerToDelete!!.customerName}")
    }
}

fun deleteInstrument() {
    val customer: Customer? = chooseCustomer()
    if (customer != null) {
        val instrument: Instrument? = chooseInstrument(customer)
        if(instrument != null) {
            val isDeleted = customer.delete(instrument.instrumentID)
            if(isDeleted)
                println("Delete Succseful")
             else println("Delete not succesful")
        }
    }
}

fun dummyData() {
    logger.info {"Dummy data successfully invoked"}
   // customerAPI.create(Customer("123Cust","Rob O'Donnell", "Gortavicory Lemybrien",null, true, "Guitar"))
   // instrumentAPI.create(Instrument("123Inst","Guitar", 500.00, 1, true, 78, "03/03/2021",null))
}
