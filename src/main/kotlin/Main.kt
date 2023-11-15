import controllers.InstrumentAPI
import controllers.CustomerAPI
import models.Instrument
import models.Customer
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger{}

val scannerInput = ScannerInput

private val instrumentAPI = InstrumentAPI(JSONSerializer(File("instruments.json")))
private val customerAPI = CustomerAPI(JSONSerializer(File("customers.json")))
fun main(args: Array<String>) {

println(2+2)
}