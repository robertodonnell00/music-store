import controllers.CustomerAPI
import models.Customer
import models.Instrument
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import persistence.YAMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

val scannerInput = ScannerInput

private var customerAPI = CustomerAPI(JSONSerializer(File("customers.json")))

// private var customerAPI = runSelectUserMenu()//CustomerAPI(JSONSerializer(File("customers.json")))
//fun roundTwoDecimals(number: Double) = "%.2f".format(number).toDouble()
val df = DecimalFormat("#.##")
fun main(args: Array<String>) {
    // dummyData()
    df.roundingMode = RoundingMode.CEILING
    runSelectUserMenu()
}


/**
 * Displays the user selection menu and returns the selected option.
 *
 * @return The user's menu selection.
 */
fun selectUserMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |         MUSIC    STORE         |
      > ----------------------------------
      > | User Select Menu               |
      > |    1) Waterford store JSON     |
      > |    2) Kilkenny Store XML       |
      > |    3) Cork Store YAML          |
      > ----------------------------------
      > |    0) Exit                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
    )
}

/**
 * Runs the user menu for selecting data storage types.
 * Loops until the user chooses to exit.
 *
 * @return The initialized CustomerAPI with the selected data storage type.
 */
fun runSelectUserMenu(): CustomerAPI {
    do {
        when (val option = selectUserMenu()) {
            1 -> {
                customerAPI.changePersistenceType(JSONSerializer(File("customers.json")))
                passCheck()
            }
            2 -> {
                customerAPI.changePersistenceType((XMLSerializer(File("customers.xml"))))
                passCheck()
            }
            3 -> {
                customerAPI.changePersistenceType((YAMLSerializer(File("customers.yaml"))))
                passCheck()
            }
            0 -> exitApp()
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the password menu and prompts the user to enter a password.
 *
 * @return The entered password as a string.
 */
fun passMenu(): String {
    print(
        """
            >__________________________________
            >|         MUSIC    STORE         |
            >|--------------------------------|
            >|   PLEASE   ENTER   PASSWORD    |
            >|________________________________|
            >|To escape (0)                   |
            >|________________________________|
            >|Enter Password : """.trimMargin(">")
    )

    return readln()
}


/**
 * Checks the entered password and runs the main menu if it is correct.
 * Loops until the user chooses to exit.
 */

fun passCheck() {
    var input: String
    do {
        input = passMenu()
        when (input) {
            "password" -> runMainMenu()
            "PASSWORD" -> runMainMenu()
            "p" -> runMainMenu() // for testing
            else -> println("Incorrect password, try again! \n Break out (0)")
        }
        println()
    } while (input != "0")
}

/**
 * Displays the main menu and returns the selected option.
 *
 * @return The user's main menu selection.
 */
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
      > |    3) Save changes             |
      > ----------------------------------
      > |    0) Exit                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
    )
}


/**
 * Runs the main menu for managing customer and instrument data.
 * Loops until the user chooses to exit.
 */
fun runMainMenu() {
    load()
    do {
        when (val option = mainMenu()) {
            1 -> runCustomerMenu()
            2 -> runInstrumentMenu()
            3 -> save()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
        save()
    } while (true)
}

/**
 * Displays the customer menu and returns the selected option.
 *
 * @return The user's selection from the customer menu.
 */
fun customerMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer Menu:                 |
      > |    1) Customer List Menu       |
      > |    2) Customer Search Menu     |
      > |    3) Customer Instrument Menu |
      > |    4) Add a new Customer       |
      > |    5) Update a Customer        |
      > |    6) Delete a Customer        |
      > |    7) Calculate total paid by  |
      > |       Customer                 |
      > ----------------------------------
      > |    0) Main Menu                |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
    )
}

/**
 * Runs the customer menu, allowing the user to navigate through various customer-related options.
 */
fun runCustomerMenu() {
    do {
        when (val option = customerMenu()) {
            1 -> runCustomerListMenu()
            2 -> runCustomerSearchMenu()
            3 -> runCustomerInstrumentMenu()
            4 -> addCustomer()
            5 -> updateCustomer()
            6 -> deleteCustomer()
            7 -> generateReceipt()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the customer list menu and returns the selected option.
 *
 * @return The user's selection from the customer list menu.
 */
fun customerListMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer List Menu:            |
      > |    1) List all customers       |
      > |    2) List VIP customers       |
      > |    3) List customer's          |
      > |       instruments              |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
    )
}

/**
 * Runs the customer list menu, allowing the user to view various lists of customers.
 */
fun runCustomerListMenu() {
    do {
        when (val option = customerListMenu()) {
            1 -> listAllCustomers()
            2 -> listVIPCustomers()
            3 -> listAllCustomerInstruments()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the customer search menu and returns the selected option.
 *
 * @return The user's selection from the customer search menu.
 */
fun customerSearchMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer Search Menu:          |
      > |    1) Search Customer by Name  |
      > |    2) Search Customer by ID    |
      > |    3) Search Customer by       |
      > |       Address                  |
      > |    4) Search Customer by       |
      > |       Preferred Instrument     |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
    )
}

/**
 * Runs the customer search menu, allowing the user to search for customers using various criteria.
 */
fun runCustomerSearchMenu() {
    do {
        when (val option = customerSearchMenu()) {
            1 -> searchCustomerByName()
            2 -> searchCustomerByID()
            3 -> searchCustomerByAddress()
            4 -> searchCustomerByPreferredInst()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the customer instrument menu and returns the selected option.
 *
 * @return The user's selection from the customer instrument menu.
 */
fun customerInstrumentMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Customer Instrument Menu:      |
      > |    1) List all Customers       |
      > |       Instruments              |
      > |    2) List all Instruments     |
      > |       that have/haven't paid   |
      > |    3) List Customers'          |
      > |       Instruments by Type      |
      > |    4) Search Customer For      |
      > |       Instrument by price      |
      > |    5) Search Customer For      |
      > |       Instrument by name       |
      > |    6) Search Customer For      |
      > |       Instrument by date       |
      > |    7) Search Customer For      |
      > |       Instrument by quantity   |
      > |    8) Search Customer For      |
      > |       Instrument by ID         |
      > |    9) Search Customer For      |
      > |       Instrument by Review     |
      > ----------------------------------
      > |    0) Main Menu                |
      > ----------------------------------
      > ==>> 
    """.trimMargin(">")
    )
}

/**
 * Runs the customer instrument menu, allowing the user to perform various actions related to customers and instruments.
 */
fun runCustomerInstrumentMenu() {
    do {
        when (val option = customerInstrumentMenu()) {
            1 -> listAllCustomerInstruments()
            2 -> listInstrumentsPaidFor()
            3 -> listInstrumentsByType()
            4 -> searchCustomerByInstrumentPrice()
            5 -> searchCustomerForInstrumentName()
            6 -> searchCustomerForInstrumentByDate()
            7 -> searchCustomerForInstrumentByQty()
            8 -> searchCustomerForInstrumentID()
            9 -> searchCustomerForInstrumentReview()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the instrument menu and returns the selected option.
 *
 * @return The user's selection from the instrument menu.
 */
fun instrumentMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Instrument Menu:               |
      > |    1) Instrument List Menu     |
      > |    2) Instrument Search Menu   |
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

/**
 * Runs the instrument menu, allowing the user to navigate through various instrument-related options.
 */
fun runInstrumentMenu() {
    do {
        when (val option = instrumentMenu()) {
            1 -> runInstrumentListMenu()
            2 -> runInstrumentSearchMenu()
            3 -> addInstrumentToCustomer()
            4 -> updateInstrument()
            5 -> deleteInstrument()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the instrument search menu and returns the selected option.
 *
 * @return The user's selection from the instrument search menu.
 */
fun instrumentSearchMenu(): Int {
    return scannerInput.readNextInt(
        """
      > ----------------------------------
      > |          MUSIC  STORE          |
      > ----------------------------------
      > | Instrument Search Menu:        |
      > |    1) Search Instrument by Name|
      > |    2) Search Instrument by     |
      > |       Price                    |
      > |    3) Search Instrument by     |
      > |       Date Received            |
      > |    4) Search Instrument by     |
      > |       Review                   |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>>
    """.trimMargin(">")
    )
}

/**
 * Runs the instrument search menu, allowing the user to search for instruments using various criteria.
 */
fun runInstrumentSearchMenu() {
    do {
        when (val option = instrumentSearchMenu()) {
            1 -> searchInstrumentByName()
            2 -> searchInstrumentByPrice()
            3 -> searchInstrumentByDate()
            4 -> searchInstrumentByReview()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

/**
 * Displays the instrument list menu and returns the selected option.
 *
 * @return The user's selection from the instrument list menu.
 */
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
      > |       Paid status              |
      > ----------------------------------
      > |    0) Back                     |
      > ----------------------------------
      > ==>>
    """.trimMargin(">")
    )
}

/**
 * Runs the instrument list menu, allowing the user to view various lists of instruments.
 */
fun runInstrumentListMenu() {
    do {
        when (val option = instrumentListMenu()) {
            1 -> listAllInstruments()
            2 -> listAllInstrumentOfType()
            3 -> listAllInstrumentPaidFor()
            0 -> break
            else -> println("Invalid option selected: $option")
        }
    } while (true)
}

// PERSISTENCE
/**
 * Exits the application after saving changes and logging the exitApp() function invocation.
 */
fun exitApp() {
    save()
    logger.info { "exitApp() function invoked" }
    exitProcess(0)
}

/**
 * Saves changes made in the application by storing customer data.
 * Logs a message indicating that changes have been saved.
 */
fun save() {
    try {
        customerAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
    logger.info { "Changes saved" }
}

/**
 * Loads customer data from a file.
 * Logs an error message if reading from the file encounters an exception.
 */
fun load() {
    try {
        customerAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

// CRUD
// CREATE


val numbers = arrayOf("1", "2", "3", "4","5","6","7","8","9","0")
/**
 * Validates a given string against a set of forbidden characters.
 *
 * This function checks if the provided string contains any of the specified forbidden characters.
 * If the string contains any forbidden character, the function returns false; otherwise, it returns true.
 *
 * @param inputString The string to be validated.
 * @param forbiddenCharacters An array of strings representing forbidden characters.
 * @return `true` if the input string does not contain any forbidden characters, `false` otherwise.
 */
fun validateName(a: String, b: Array<String>):Boolean {
    b.forEach{number ->
        if(a.contains(number))
            return false
    }
    return true
}


/**
 * Adds a new customer to the application by gathering input from the user.
 * Prints success or failure messages based on the outcome.
 */

fun addCustomer() {
    println("Suggested Customer ID:")
    val customerID = readNextInt("Suggested Customer ID: ${customerAPI.numberOfCustomers()} \nEnter Customer ID: ")
    var customerName = readNextLine("Enter Customer Name: ")
    while((!validateName(customerName,numbers)) || customerName.length > 15){
        customerName = readNextLine("Invalid name, cannot have numbers or be greater than 15 characters\nTry again: ")
    }
    var customerAddress = readNextLine("Enter Customer Address: ")
    while (customerAddress.length > 20){
        customerAddress = readNextLine("Invalid address, cannot be longer than 20 characters\nTry again:")
    }
    val vipCustomer = false
    val preferredInstrument = selectInstrumentType()

    val isAdded = customerAPI.create(
        Customer(
            customerID = customerID,
            customerName = customerName,
            customerAddress = customerAddress,
            vipCustomer = vipCustomer,
            preferredInstrument = preferredInstrument
        )
    )

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

/**
 * Chooses a customer from the list of existing customers.
 *
 * @return The selected customer or null if no customer is chosen.
 */
private fun chooseCustomer(): Customer? {
    listAllCustomers()
    if (customerAPI.numberOfCustomers() > 0) {
        val customer = customerAPI.findCustomer(readNextInt("\nEnter index of the customer: "))
        if (customer != null) return customer else { println("Customer ID invalid") }
    }
    return null
}

/**
 * Chooses an instrument from the list of instruments associated with a customer.
 *
 * @param customer The customer for whom the instrument is chosen.
 * @return The selected instrument or null if no instrument is chosen.
 */
private fun chooseInstrument(customer: Customer): Instrument? {
    return if (customerAPI.numberOfCustomers() > 0) {
        println(customer.listInstruments())
        customer.findInstrument(readNextInt("\nEnter ID of the Instrument: "))
    } else {
        println("No instruments for chosen Customer")
        null
    }
}

/**
 * Adds an instrument to a customer's collection by gathering input from the user.
 * Prints success or failure messages based on the outcome.
 */
fun addInstrumentToCustomer() {
    val customer: Customer? = chooseCustomer()

    if (customer != null) {
        println("Suggested Customer ID:")
        val instrumentID = readNextInt("Suggested Customer ID: ${customer.numberOfInstruments()} \nEnter Instrument ID: ")
        val instrumentName = readNextLine("Enter Instrument Name: ")
        val instrumentType = selectInstrumentType()
        val price = readNextDouble("Enter the price of the instrument: ")
        val quantityBought = readNextInt("Enter the quantity of instruments the customer bought: ")
        println("Did the customer pay for instrument: ")
        val isPaidFor = readln().toBoolean()
        var instrumentReview = readNextInt("Enter the review for instrument: ")
        while (instrumentReview < 0 || instrumentReview > 101){
            instrumentReview = readNextInt("Invalid review, must be whole number between 0 and 100\nTry again: ")
        }
        var dateReceived = readNextLine("Enter the date the customer received instrument: ")
        while (dateReceived.length > 10 ||(!dateReceived.contains("/"))) {
            dateReceived = readNextLine("Invalid date received, please enter date in format (DD/MM/YYYY)\nTry again: ")
        }
        val isAdded = customer.create(Instrument(instrumentID, instrumentName, instrumentType, price, quantityBought, isPaidFor, instrumentReview, dateReceived, customer.customerID))
        if (isAdded) {
            println("Added Successfully")
        } else {
            println("Add Failed")
        }
    }
}

// READ

// LIST Customers
/**
 * Prints the list of all customers using the CustomerAPI's `listAllCustomers` function.
 */
fun listAllCustomers() {
    println(customerAPI.listAllCustomers())
}

/**
 * Prints the list of all instruments using the CustomerAPI's `listAllInstruments` function.
 */
fun listAllInstruments() {
    println(customerAPI.listAllInstruments())
}

/**
 * Prints the list of VIP customers using the CustomerAPI's `listVIPCustomers` function.
 */
fun listVIPCustomers() {
    println(customerAPI.listVIPCustomers())
}

// SEARCH CUSTOMER
/**
 * Searches for customers based on their preferred instrument type.
 *
 * @see CustomerAPI.searchCustomersByPreferredInst
 */
fun searchCustomerByPreferredInst() {
    val searchType = readNextLine("Enter type of Instrument: ")
    val searchResults = customerAPI.searchCustomersByPreferredInst(searchType)
    if (searchResults.isEmpty()) {
        println("No customers found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for customers by their name using the CustomerAPI's `searchByName` function.
 *
 * @see CustomerAPI.searchByName
 */
fun searchCustomerByName() {
    val searchName = readNextLine("Enter the Customer's name: ")
    val searchResults = customerAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println("No customers found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for customers by their address using the CustomerAPI's `searchByAddress` function.
 *
 * @see CustomerAPI.searchByAddress
 */
fun searchCustomerByAddress() {
    val searchAddress = readNextLine("Enter the Customer's Address: ")
    val searchResults = customerAPI.searchByAddress(searchAddress)
    if (searchResults.isEmpty()) {
        println("No customers found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for customers by their ID using the CustomerAPI's `searchByID` function.
 *
 * @see CustomerAPI.searchByID
 */
fun searchCustomerByID() {
    val searchID = readNextInt("Enter the Customer's ID: ")
    val searchResults = customerAPI.searchByID(searchID)
    if (searchResults.isEmpty()) {
        println("No customers found")
    } else {
        println(searchResults)
    }
}

// When you know the customer but not instrument
/**
 * Searches for an instrument associated with a customer by its price.
 *
 * @see Customer.searchByPrice
 */
fun searchCustomerByInstrumentPrice() {
    val customer = chooseCustomer()
    val option = readNextDouble("Enter the price of the Instrument: ")
    val result = customer!!.searchByPrice(option)
    if (result.isEmpty()) {
        println("Nothing found")
    } else {
        println(result)
    }
}

/**
 * Searches for an instrument associated with a customer by its name.
 *
 * @see Customer.searchByInstrumentName
 */
fun searchCustomerForInstrumentName() {
    val customer = chooseCustomer()
    val option = readNextLine("Enter name of the instrument: ")
    val result = customer!!.searchByInstrumentName(option)
    if (result.isEmpty()) {
        println("Nothing Found")
    } else {
        println(result)
    }
}

/**
 * Searches for an instrument associated with a customer by its review.
 *
 * @see Customer.searchByReview
 */
fun searchCustomerForInstrumentReview() {
    val customer = chooseCustomer()
    val option = readNextInt("Enter the review for the Instrument: ")
    val result = customer!!.searchByReview(option)
    if (result.isEmpty()) {
        println("Nothing Found")
    } else {
        println(result)
    }
}

/**
 * Searches for an instrument associated with a customer by the date it was received.
 *
 * @see Customer.searchByDateReceived
 */
fun searchCustomerForInstrumentByDate() {
    val customer = chooseCustomer()
    val option = readNextLine("Enter the date Instrument was received: ")
    val result = customer!!.searchByDateReceived(option)
    if (result.isEmpty()) {
        println("Nothing Found")
    } else {
        println(result)
    }
}

/**
 * Searches for instruments associated with a customer by the quantity purchased.
 *
 * @see Customer.searchByQuantityBought
 */
fun searchCustomerForInstrumentByQty() {
    val customer = chooseCustomer()
    val option = readNextInt("Enter the quantity of Instrument purchased: ")
    val result = customer!!.searchByQuantityBought(option)
    if (result.isEmpty()) {
        println("Nothing Found")
    } else {
        println(result)
    }
}

/**
 * Searches for an instrument associated with a customer by its ID.
 *
 * @see Customer.searchInstrumentByID
 */
fun searchCustomerForInstrumentID() {
    val customer = chooseCustomer()
    val option = readNextInt("Enter the ID of Instrument: ")
    val result = customer!!.searchInstrumentByID(option)
    if (result.isEmpty()) {
        println("Nothing Found")
    } else {
        println(result)
    }
}

/**
 * Prints the list of instruments associated with a specific customer using the `listInstruments` function.
 */
fun listAllCustomerInstruments() {
    val customer = chooseCustomer()
    println(customer!!.listInstruments())
}

/**
 * Lists instruments associated with a customer based on whether they are paid for or not.
 *
 * @see Customer.listInstrumentsPaidFor
 */
fun listInstrumentsPaidFor() {
    val customer = chooseCustomer()
    val option = (readNextLine("Has the customer paid?(true/false): ")).toBoolean()
    val result = customer!!.listInstrumentsPaidFor(option)
    if (result.isEmpty()) {
        println("Nothing Found")
    } else {
        println(result)
    }
}

/**
 * Lists instruments associated with a customer based on the instrument type.
 *
 * @see Customer.listInstrumentsByType
 */
fun listInstrumentsByType() {
    val customer = chooseCustomer()
    val searchString = readNextLine("Enter the type of instrument: ")
    val result = customer!!.listInstrumentsByType(searchString)
    if (result.isEmpty()) {
        println("Nothing found")
    } else {
        println(result)
    }
}

/**
 * Searches for instruments by name using the CustomerAPI's `searchInstrumentByName` function.
 *
 * @see CustomerAPI.searchInstrumentByName
 */
fun searchInstrumentByName() {
    val name = readNextLine("Enter the name of the instrument: ")
    val searchResults = customerAPI.searchInstrumentByName(name)
    if (searchResults.isEmpty()) {
        println("No Instrument by that name")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for instruments by date using the CustomerAPI's `searchInstrumentByDate` function.
 *
 * @see CustomerAPI.searchInstrumentByDate
 */
fun searchInstrumentByDate() {
    val date = readNextLine("Enter date the Instrument was purchased: ")
    val searchResults = customerAPI.searchInstrumentByDate(date)
    if (searchResults.isEmpty()) {
        println("No Instrument sold on given date")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for instruments by price using the CustomerAPI's `searchInstrumentByPrice` function.
 *
 * @see CustomerAPI.searchInstrumentByPrice
 */
fun searchInstrumentByPrice() {
    val price = readNextDouble("Enter the price of the instrument: ")
    val searchResults = customerAPI.searchInstrumentByPrice(price)
    if (searchResults.isEmpty()) {
        println("No Instrument of that price")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for instruments by review using the CustomerAPI's `searchInstrumentByReview` function.
 *
 * @see CustomerAPI.searchInstrumentByReview
 */
fun searchInstrumentByReview() {
    val review = readNextInt("Enter the review for the instrument: ")
    val searchResults = customerAPI.searchInstrumentByReview(review)
    if (searchResults.isEmpty()) {
        println("No Instrument of that price")
    } else {
        println(searchResults)
    }
}

/**
 * Lists instruments of a specific type using the CustomerAPI's `searchInstrumentByType` function.
 *
 * @see CustomerAPI.searchInstrumentByType
 */
fun listAllInstrumentOfType() {
    val type = selectInstrumentType()
    val searchResults = customerAPI.searchInstrumentByType(type)
    if (searchResults.isEmpty()) {
        println("No Instrument of that type")
    } else {
        println(searchResults)
    }
}

/**
 * Updates customer information based on user input.
 *
 * @see CustomerAPI.updateCustomer
 */
fun listAllInstrumentPaidFor() {
    val type = (
        readNextLine(
            "For instruments that are paid for, enter true\n" +
                "For instruments NOT paid, enter false:  "
        )
        ).toBoolean()
    val searchResults = customerAPI.listInstrumentsPaidFor(type)
    if (searchResults.isEmpty()) {
        println("No Instruments available")
    } else {
        println(searchResults)
    }
}

// UPDATE

/**
 * Updates customer information based on user input.
 *
 * @see CustomerAPI.updateCustomer
 */
fun updateCustomer() {
    listAllCustomers()
    if (customerAPI.numberOfCustomers() > 0) {
        // only ask the user to choose the customer if it exists
        val indexToUpdate = readNextInt("Enter the index of the Customer you wish to update: ")
        if (customerAPI.isValidIndex(indexToUpdate)) {
            val customerID = readNextInt("Enter customer ID: ")
            val customerName = readNextLine("Enter customer name: ")
            val customerAddress = readNextLine("Enter customer address: ")
            val customerVIP = false //should be false by default
            val preferredInstrument = selectInstrumentType()
            if (customerAPI.updateCustomer(indexToUpdate, Customer(customerID, customerName, customerAddress, mutableSetOf(), customerVIP, preferredInstrument))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes of that index number")
        }
    }
}

/**
 * Updates instrument information based on user input.
 *
 * @see Customer.updateInstrument
 */
fun updateInstrument() {
    val customer: Customer? = chooseCustomer()
    if (customer != null) {
        val instrument: Instrument? = chooseInstrument(customer)

        if (instrument != null) {
            val instrumentID = readNextInt("Enter the instrument ID: ")
            val instrumentName = readNextLine("Enter Instrument Name: ")
            val instrumentType = selectInstrumentType()
            val price = readNextDouble("Enter the price of instrument: ")
            val quantityBought = readNextInt("Enter the quantity ordered: ")
            println("Has the instrument been paid for? ")
            val isPaidFor = readln().toBoolean()
            val instrumentReview = readNextInt("Enter review out of 100: ")
            val dateReceived = readNextLine("Enter date the customer received instrument: ")

            if (customer.updateInstrument(
                    instrument.instrumentID,
                    Instrument(
                            instrumentID, instrumentType, instrumentName, price, quantityBought,
                            isPaidFor, instrumentReview, dateReceived, customer.customerID
                        )
                )
            ) {
                println("Instrument details updated")
            } else {
                println("Invalid Instrument ID")
            }
        }
    }
}

// DELETE
/**
 * Deletes a customer based on user input.
 *
 * @see CustomerAPI.deleteCustomer
 */
fun deleteCustomer() {
    listAllCustomers()
    if (customerAPI.numberOfCustomers() > 0) {
        // only ask user to choose customer to be deleted if it exists
        val indexToDelete = readNextInt("Enter the index of the customer you wish to delete: ")
        // pass index of customer to controller for deleting and check for success
        val customerToDelete = customerAPI.deleteCustomer(indexToDelete)

        println("Delete Successful! Customer deleted : ${customerToDelete!!.customerName}")
    }
}

/**
 * Deletes an instrument based on user input.
 *
 * @see Customer.delete
 */
fun deleteInstrument() {
    val customer: Customer? = chooseCustomer()
    if (customer != null) {
        val instrument: Instrument? = chooseInstrument(customer)
        if (instrument != null) {
            val isDeleted = customer.delete(instrument.instrumentID)
            if (isDeleted) {
                println("Delete successful")
            } else {
                println("Delete not successful")
            }
        }
    }
}

/**
 * Allows the user to select an instrument type from a predefined list or enter a new type.
 * Displays the available instrument categories and prompts the user for selection.
 *
 * @return Selected instrument type.
 */
fun selectInstrumentType(): String {
    val instrumentTypes = listOf(
        "Guitar",
        "Bass",
        "Strings",
        "Wind",
        "Drums",
        "Keys",
        "Electric",
        "Acoustic"
    )
    println("Which category does the Instrument fall under?")
    instrumentTypes.forEachIndexed { index, category ->
        println("${index + 1}) $category")
    }
    println("9) Enter new category option")

    return when (val selectedOption = readNextInt("Enter the number for the category: ")) {
        in 1..instrumentTypes.size -> {
            instrumentTypes[selectedOption - 1]
        }
        instrumentTypes.size + 1 -> {
            readNextLine("Enter a new type: ")
        }
        else -> {
            println("Invalid category selected, defaulting to Other.")
            "Other"
        }
    }
}

/**
 * Generates a receipt for a customer's purchased instruments.
 * Displays details such as customer information, instrument details, total spent, and total owed.
 * Updates the customer's VIP status if the total spent exceeds €500 and they are not already a VIP.
 */
fun generateReceipt() {
    val customer = chooseCustomer()

    println(
        """
        >----------------------------------
        >|  RECEIPT FOR:                  
        >|  Customer: ${customer!!.customerName} ID: ${customer.customerID}          
   """.trimMargin(">")
    )
    var totalOwed = 0.0
    var totalPrice = 0.0
    for (instrument in customer.itemsBought) {
        if (instrument.isPaidFor) {
            totalPrice = (totalPrice + (instrument.price * instrument.qauntityBought))
        } else {
            totalOwed += (instrument.price * instrument.qauntityBought)
        }

        println(
            """
        >----------------------------------
        >|  Instrument Name: ${instrument.instrumentName}          
        >|  Instrument ID: ${instrument.instrumentID}          
        >|  Purchased On: ${instrument.dateReceived}          
        >|  Quantity: ${instrument.qauntityBought}            
        >|  Price: €${instrument.price}                
        >----------------------------------
        >| Total for ${instrument.instrumentName}: €${df.format(instrument.price * instrument.qauntityBought)}                                
    """.trimMargin(">")
        )
    }
    if (totalPrice == 0.0) {
        println("No items purchased")
    } else {
        println(
            """
        >|                                 
        >----------------------------------
        >|   Total Spent: €${df.format(totalPrice)}     
        >|   Total Owed: €${df.format(totalOwed)}       
        
    """.trimMargin(">")
        )
    }

    if ((totalPrice > 500.0) && !customer.vipCustomer) {
        val updateStatus = readNextLine("Customer is eligible for VIP status \nUpdate status? (true/false): ")
        if (updateStatus == "true") {
            // What if customers ID is different from their index?
            customerAPI.updateStatus(customer.customerID)
            // customerAPI.updateStatus(customer.indexOf(customer))
        }
    }
}
