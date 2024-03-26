package com.example.mikesandbox

import android.util.Log
import java.time.*
import java.util.Calendar

object CalendarHelper{
    val DayArray = arrayListOf("*","Lunes","Martes","Mier","Jueves","Viernes","Sabado","Domingo")

    private var nativeDate: DayModel = DayModel("12",10,2002)

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
        Log.d("Called Days", "Year: ${this.nativeDate.year} / Month: ${this.nativeDate.month}  Dias : ${calendario.getActualMaximum(Calendar.DAY_OF_MONTH)}")
        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun nGetMonth():Int {
        return this.nativeDate.month
    }

    fun nGetYear():Int{
        return this.nativeDate.year.toInt()
    }

    fun nAddMonth(dayReceived:DayModel, i: Int):DayModel{
        return if(dayReceived.month == 11){
            DayModel(dayReceived.day,0,dayReceived.year+1)
        }else{
            DayModel(dayReceived.day,dayReceived.month+1,dayReceived.year)
        }
    }

    fun nGetDummySpaces(): Int {
        val calendario = Calendar.getInstance()
        calendario.set(this.nativeDate.year,this.nativeDate.month,1)
        Log.d("Date ver: ",calendario.get(Calendar.DAY_OF_WEEK).toString())
        return calendario.get(Calendar.DAY_OF_WEEK)-1
    }
}