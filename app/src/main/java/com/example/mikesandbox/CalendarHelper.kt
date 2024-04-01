package com.example.mikesandbox

import android.util.Log
import java.time.*
import java.util.Calendar

object CalendarHelper{
    private var nativeDate: DayModel = DayModel("12",10,2002)
    private var disabledDates : ArrayList<Int> = arrayListOf<Int>()

    //Native Implementation
    fun nSetDate(receivedDate:DayModel){
        this.nativeDate = receivedDate
    }

    fun nGetDate():DayModel{
        return this.nativeDate
    }

    fun nGetNumberOfDays():Int{
        val calendario = Calendar.getInstance()
        calendario.set(Calendar.YEAR, this.nativeDate.year)
        calendario.set(Calendar.MONTH,this.nativeDate.month)
        val numberOfDays = calendario.getActualMaximum(Calendar.DAY_OF_MONTH)
        return numberOfDays
    }

    fun nGetMonth():Int {
        return this.nativeDate.month
    }

    fun nGetYear():Int{
        return this.nativeDate.year.toInt()
    }

    fun nAddMonth(dayReceived:DayModel):DayModel{
        return if(dayReceived.month == 11){
            DayModel(dayReceived.day,0,dayReceived.year+1)
        }else{
            DayModel(dayReceived.day,dayReceived.month+1,dayReceived.year)
        }
    }

    fun nSetDisabledDates(disabledDates : ArrayList<Int>){
        this.disabledDates.addAll(disabledDates)
    }

    fun nGetDisabledDates():ArrayList<Int>{
        return this.disabledDates
    }

    fun nGetDummySpaces(): Int {
        val calendario = Calendar.getInstance()
        calendario.set(this.nativeDate.year,this.nativeDate.month,1)
        return calendario.get(Calendar.DAY_OF_WEEK)-1
    }
}