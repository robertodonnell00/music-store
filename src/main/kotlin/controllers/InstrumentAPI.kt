package controllers

import models.Customer
import models.Instrument

class InstrumentAPI {
    private var instruments = ArrayList<Instrument>()

    // CRUD
    //CREATE
    fun add(instrument: Instrument): Boolean {
        return instruments.add(instrument)
    }

    //READ

    private fun formatListString(instrumentToFormat: List<Instrument>): String =
        instrumentToFormat
            .joinToString("\n") { instrument ->
                instruments.indexOf(instrument).toString() + ": " + instrument.toString()
            }

    fun isValidListIndex(index:Int, list: List<Instrument>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listAllInstruments(): String=
        if(instruments.isEmpty()) "No instruments are stored"
        else formatListString(instruments)


    fun findInstrument(index: Int): Instrument? {
        return if(isValidListIndex(index, instruments)) {
            instruments[index]
        } else null
    }


    fun updateInstrument(indexToUpdate: Int, instrument: Instrument?): Boolean {
        //find instrument using index
        var foundInstrument = findInstrument(indexToUpdate)
        //if instrument exists, use details as parameters to update instrument
        if((foundInstrument != null) && (instrument != null)){
            foundInstrument.instrumentID = instrument.instrumentID
            foundInstrument.instrumentType = instrument.instrumentType
            foundInstrument.instrumentReview = instrument.instrumentReview
            foundInstrument.price = instrument.price
            foundInstrument.qauntityBought = instrument.qauntityBought
            foundInstrument.isPaidFor = instrument.isPaidFor
            foundInstrument.instrumentReview = instrument.instrumentReview
            foundInstrument.dateReceived = instrument.dateReceived
            foundInstrument.customerBought = instrument.customerBought
            return true
        }
        return false
    }


}