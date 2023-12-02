import controllers.CustomerAPI
import models.Instrument
import models.Customer
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import persistence.YAMLSerializer
import persistence.Serializer
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.text.DecimalFormat
import java.math.RoundingMode

private val logger = KotlinLogging.logger{}

val scannerInput = ScannerInput

private var customerAPI = CustomerAPI(JSONSerializer(File("customers.json")))

//private var customerAPI = runSelectUserMenu()//CustomerAPI(JSONSerializer(File("customers.json")))

fun roundTwoDecimals(number: Double) = "%.2f".format(number).toDouble()
val df = DecimalFormat("#.##")
fun main(args: Array<String>) {
    //dummyData()
    df.roundingMode = RoundingMode.CEILING
     runSelectUserMenu()
}


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

    fun runSelectUserMenu(): CustomerAPI {
        do {
            when (val option = selectUserMenu()) {
                1-> {customerAPI.changePersistenceType(JSONSerializer(File("customers.json")))
                    passCheck()
                }
                2-> {customerAPI.changePersistenceType((XMLSerializer(File("customers.xml"))))
                    passCheck()
                }
                3-> {customerAPI.changePersistenceType((YAMLSerializer(File("customers.yaml"))))
                    passCheck()
                }
                0-> exitApp()
                else -> println("Invalid option selected: $option")
            }
        } while (true)
    }


fun passMenu(): String {
        print("""
            >__________________________________
            >|         MUSIC    STORE         |
            >|--------------------------------|
            >|   PLEASE   ENTER   PASSWORD    |
            >|________________________________|
            >|To escape (0)                   |
            >|________________________________|
            >|Enter Password : """.trimMargin(">"))

        return readLine()!!.toString()
    }

fun passCheck() {
    var input: String
    do {
        input = passMenu()
        when(input) {
            "password"  -> runMainMenu()
            "PASSWORD" -> runMainMenu()
            "p" -> runMainMenu() //for testing
            else -> println("Incorrect password, try again! \n Break out (0)")
        }
        println()
    } while (input != "0")
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
      > |    3) Save changes             |
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
                3 -> save()
                0 -> break
                else -> println("Invalid option selected: $option")
            }
            save()
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


//PERSISTENCE

fun exitApp(){
    save()
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
    println("Suggested Customer ID")
    val customerID = readNextInt("Suggested Customer ID: ${customerAPI.numberOfCustomers()} \nEnter Customer ID: ")
        //customerAPI.numberOfCustomers()
    val customerName = readNextLine("Enter Customer Name: ")
    val customerAddress = readNextLine("Enter Customer Address: ")
    println("Enter Customer VIP Status (true/false): ")
    val vipCustomer = readln().toBoolean()
    val preferredInstrument = selectInstrumentType()

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
        val customer = customerAPI.findCustomer(readNextInt("\nEnter index of the customer: "))
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
        val instrumentName = readNextLine("Enter Instrument Name: ")
        val instrumentType = selectInstrumentType()
        val price = readNextDouble("Enter the price of the instrument: ")
        val quantityBought = readNextInt("Enter the quantity of instruments the customer bought: ")
        println("Did the customer pay for instrument: ")
        val isPaidFor = readln().toBoolean()
        val instrumentReview = readNextInt("Enter the review for instrument: ")
        val dateReceived = readNextLine("Enter the date the customer received instrument: ")


        val isAdded = customer.create(Instrument(instrumentID, instrumentName, instrumentType,price,quantityBought, isPaidFor,instrumentReview,dateReceived, customer.customerID))
        if (isAdded) {
            println("Added Successfully")
        } else {
            println("Add Failed")
        }

    }

}

//READ

//LIST Customers
fun listAllCustomers() {
    println(customerAPI.listAllCustomers())
}

fun listAllInstruments() {
    println(customerAPI.listAllInstruments())
}

fun listVIPCustomers() {
    println(customerAPI.listVIPCustomers())
}


//SEARCH CUSTOMER

fun searchCustomerByPreferredInst() {
    val searchType = readNextLine("Enter type of Instrument: ")
    val searchResults = customerAPI.searchCustomersByPreferredInst(searchType)
    if(searchResults.isEmpty())
        println("No customers found")
    else println(searchResults)
}

fun searchCustomerByName() {
    val searchName = readNextLine("Enter the Customer's name: ")
    val searchResults = customerAPI.searchByName(searchName)
    if(searchResults.isEmpty())
        println("No customers found")
    else println(searchResults)
}
fun searchCustomerByAddress() {
    val searchAddress = readNextLine("Enter the Customer's Address: ")
    val searchResults = customerAPI.searchByAddress(searchAddress)
    if(searchResults.isEmpty())
        println("No customers found")
    else println(searchResults)
}
fun searchCustomerByID() {
    val searchID = readNextInt("Enter the Customer's ID: ")
    val searchResults = customerAPI.searchByID(searchID)
    if(searchResults.isEmpty())
        println("No customers found")
    else println(searchResults)
}

// When you know the customer but not instrument

fun searchCustomerByInstrumentPrice() {
    val customer = chooseCustomer()
    val option = readNextDouble("Enter the price of the Instrument: ")
    val result = customer!!.searchByPrice(option)
    if(result.isEmpty())println("Nothing found")
    else println(result)
}

fun searchCustomerForInstrumentName() {
    val customer = chooseCustomer()
    val option = readNextLine("Enter name of the instrument: ")
    val result = customer!!.searchByInstrumentName(option)
    if(result.isEmpty()) println("Nothing Found")
    else println(result)
}

fun searchCustomerForInstrumentReview() {
    val customer = chooseCustomer()
    val option = readNextInt("Enter the review for the Instrument: ")
    val result = customer!!.searchByReview(option)
    if(result.isEmpty()) println("Nothing Found")
    else println(result)
}

fun searchCustomerForInstrumentByDate() {
    val customer = chooseCustomer()
    val option = readNextLine("Enter the date Instrument was received: ")
    val result = customer!!.searchByDateReceived(option)
    if(result.isEmpty()) println("Nothing Found")
    else println(result)
}

fun searchCustomerForInstrumentByQty() {
    val customer = chooseCustomer()
    val option = readNextInt("Enter the quantity of Instrument purchased: ")
    val result = customer!!.searchByQuantityBought(option)
    if(result.isEmpty()) println("Nothing Found")
    else println(result)
}

fun searchCustomerForInstrumentID() {
    val customer = chooseCustomer()
    val option = readNextInt("Enter the ID of Instrument: ")
    val result = customer!!.searchInstrumentByID(option)
    if(result.isEmpty()) println("Nothing Found")
    else println(result)
}




fun listAllCustomerInstruments() {
    val customer = chooseCustomer()
    println(customer!!.listInstruments())
}

fun listInstrumentsPaidFor() {
    val customer = chooseCustomer()
    val option = (readNextLine("Has the customer paid?(true/false): ")).toBoolean()
    val result = customer!!.listInstrumentsPaidFor(option)
    if(result.isEmpty()) println("Nothing Found")
    else println(result)
}

fun listInstrumentsByType() {
    val customer = chooseCustomer()
    val searchString = readNextLine("Enter the type of instrument: ")
    val result = customer!!.listInstrumentsByType(searchString)
    if(result.isEmpty())
        println("Nothing found")
    else println(result)
}


fun searchInstrumentByName() {
    val name = readNextLine("Enter the name of the instrument: ")
    val searchResults = customerAPI.searchInstrumentByName(name)
    if(searchResults.isEmpty()) println("No Instrument by that name")
    else println(searchResults)
}

fun searchInstrumentByDate() {
    val date = readNextLine("Enter date the Instrument was purchased: ")
    val searchResults = customerAPI.searchInstrumentByDate(date)
    if(searchResults.isEmpty()) println("No Instrument sold on given date")
    else println(searchResults)
}

fun searchInstrumentByPrice() {
    val price = readNextDouble("Enter the price of the instrument: ")
    val searchResults = customerAPI.searchInstrumentByPrice(price)
    if(searchResults.isEmpty()) println("No Instrument of that price")
    else println(searchResults)
}

fun searchInstrumentByReview() {
    val review = readNextInt("Enter the review for the instrument: ")
    val searchResults = customerAPI.searchInstrumentByReview(review)
    if(searchResults.isEmpty()) println("No Instrument of that price")
    else println(searchResults)
}


fun listAllInstrumentOfType() {
        val type = selectInstrumentType()
        val searchResults = customerAPI.searchInstrumentByType(type)
        if(searchResults.isEmpty()) println("No Instrument of that type")
        else println(searchResults)
}

fun listAllInstrumentPaidFor() {
        val type = (readNextLine("For instruments that are paid for, enter true\n" +
                "For instruments NOT paid, enter false:  ")).toBoolean()
        val searchResults = customerAPI.listInstrumentsPaidFor(type)
        if(searchResults.isEmpty()) println("No Instruments available")
        else println(searchResults)
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
            val customerVIP = false
            val preferredInstrument = selectInstrumentType()
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
            val instrumentName = readNextLine("Enter Instrument Name: ")
            val instrumentType = selectInstrumentType()
            val price = readNextDouble("Enter the price of instrument: ")
            val quantityBought = readNextInt("Enter the quantity ordered: ")
            println("Has the instrument been paid for? ")
            val isPaidFor = readln().toBoolean()
            val instrumentReview = readNextInt("Enter review out of 100: ")
            val dateReceived = readNextLine("Enter date the customer received instrument: ")

            if(customer.updateInstrument(instrument.instrumentID, Instrument(
                        instrumentID, instrumentType, instrumentName, price, quantityBought,
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
                println("Delete successful")
             else println("Delete not successful")
        }
    }
}

fun selectInstrumentType(): String {
    val instrumentTypes = listOf(
        "Guitar", "Bass", "Strings", "Wind", "Drums", "Keys", "Electric", "Acoustic"
    )
    println("Which category does the Instrument fall under?")
    instrumentTypes.forEachIndexed {index, category ->
        println("${index + 1}) $category")}
    println("9) Enter new category option")

    return when (val selectedOption = readNextInt("Enter the number for the category: ")) {
        in 1..instrumentTypes.size -> {
            instrumentTypes[selectedOption-1]
        }
        instrumentTypes.size +1 -> {
            readNextLine("Enter a new type: ")
        }
        else -> {
            println("Invalid category selected, defaulting to Other.")
            "Other"
        }
    }
}

fun generateReceipt() {
    val customer = chooseCustomer()

    println("""
        >----------------------------------
        >|  RECEIPT FOR:                  
        >|  Customer: ${customer!!.customerName} ID: ${customer.customerID}          
   """.trimMargin(">"))
    var totalOwed= 0.0
    var totalPrice = 0.0
    for(instrument in customer.itemsBought) {
        if(instrument.isPaidFor)
        totalPrice = (totalPrice + (instrument.price * instrument.qauntityBought))
        else totalOwed = totalOwed + (instrument.price * instrument.qauntityBought)

        println("""
        >----------------------------------
        >|  Instrument Name: ${instrument.instrumentName}          
        >|  Instrument ID: ${instrument.instrumentID}          
        >|  Purchased On: ${instrument.dateReceived}          
        >|  Quantity: ${instrument.qauntityBought}            
        >|  Price: €${instrument.price}                
        >----------------------------------
        >| Total for ${instrument.instrumentName}: €${df.format(instrument.price * instrument.qauntityBought)}                                
    """.trimMargin(">"))
    }
    if (totalPrice == 0.0) println("No items purchased")
    else println("""
        >|                                 
        >----------------------------------
        >|   Total Spent: €${df.format(totalPrice)}     
        >|   Total Owed: €${df.format(totalOwed)}       
        
    """.trimMargin(">"))

    if ((totalPrice>500.0) && !customer.vipCustomer){
        val updateStatus = readNextLine("Customer is eligible for VIP status \nUpdate status? (true/false): ")
        if(updateStatus == "true") {
            //What if customers ID is different than their index?
            customerAPI.updateStatus(customer.customerID)
            //customerAPI.updateStatus(customer.indexOf(customer))
        }
    }

}



