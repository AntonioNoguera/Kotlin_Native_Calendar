package com.example.mikesandbox

import android.util.Log
import java.time.*

object CalendarHelper{
    val DayArray = arrayListOf("*","Lunes","Martes","Mier","Jueves","Viernes","Sabado","Domingo")
    val MonthArray = arrayListOf("*","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")

    private var date:LocalDate = LocalDate.of(2010,1,11)

    fun setDate(year:LocalDate){
        this.date = year
    }

    fun getDate():LocalDate{
        return this.date!!
    }

    fun getNumberOfDays():Int{
        return this.date!!.lengthOfMonth()
    }

    fun getMonthName():String{

        return MonthArray[this.date!!.monthValue]
    }

    fun getDayName():String{
        return MonthArray[this.date!!.dayOfYear]
    }

    fun getDummySpaces(): Int {
        val firstOfTheMonth = LocalDate.of(date.year, date.monthValue, 1)
        val day = firstOfTheMonth.dayOfWeek.value
        Log.d("Object list",day.toString())
        return day
    }

}