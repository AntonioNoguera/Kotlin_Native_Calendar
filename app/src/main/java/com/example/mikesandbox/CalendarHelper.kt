package com.example.mikesandbox

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.*

object CalendarHelper{
    val DayArray = arrayListOf("*","Lunes","Martes","Mier","Jueves","Viernes","Sabado","Domingo")
    val MonthArray = arrayListOf("*","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")

    private var date:LocalDate? = null

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
        return DayArray[this.date!!.monthValue]
    }

    fun getDayName():String{
        return MonthArray[this.date!!.dayOfYear]
    }

    fun getDummySpaces():Int{
        return 2
    }

}