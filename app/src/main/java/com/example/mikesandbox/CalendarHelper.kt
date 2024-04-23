package com.example.mikesandbox

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.mikesandbox.chico.CalendarAdapter
import com.example.mikesandbox.chico.CalendarDateModel
import com.example.mikesandbox.grande.CalendarDayAdapter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.ceil

object CalendarHelper {
    private var calendarDates: CalendarDateModel = CalendarDateModel(Calendar.getInstance().time)
    private var disabledDates : ArrayList<Int> = arrayListOf<Int>()
    private var firstDayOfNextMonth = 0
    private var currentDateSelected: Date? = null
    private var lastSelectedMonthlyHolder: CalendarDayAdapter.ViewHolder? = null
    private var lastSelectedWeeklyHolder: CalendarAdapter.CalendarViewHolder? = null
    private var position: Int? = null

    // Envia mes y año actual, para saber la cantidad de dias del mes
    fun nSetDate(receivedDate: Calendar){
        this.calendarDates.data = receivedDate.time
    }

    // La devuelve como objeto la fecha seleccionada
    fun nGetDate():CalendarDateModel {
        return this.calendarDates
    }

    // Calcula el número total de días en el mes actual
    fun nGetNumberOfDays():Int{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, this.calendarDates.calendarYear.toInt())
        calendar.set(Calendar.MONTH,this.calendarDates.calendarMonthDate.toInt() - 1)
        val numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return numberOfDays
    }

    // Devuelve el mes actual (de base 0) del objeto nativeDate
    fun nGetMonth():Int {
        return this.calendarDates.calendarMonthDate.toInt() - 1
    }

    // devuelve el año actual del objeto nativeDate
    fun nGetYear():Int{
        return this.calendarDates.calendarYear.toInt()
    }

    // Toma un objeto DayModel y devuelve un nuevo objeto DayModel que representa la fecha después de agregar un mes. Se encarga del cambio de año si es necesario
    fun nAddMonth(dayReceived: Calendar): CalendarDateModel {
        dayReceived.add(Calendar.MONTH, 1)
        return CalendarDateModel(data = dayReceived.time)
    }

    // Establece la lista de fechas deshabilitadas agregando el ArrayList de fechas deshabilitadas proporcionado a la propiedad disabledDates
    fun nSetDisabledDates(disabledDates : ArrayList<Int>){
        this.disabledDates.addAll(disabledDates)
    }

    // devuelve una copia de la lista actual de fechas deshabilitadas.
    fun nGetDisabledDates():ArrayList<Int>{
        return this.disabledDates
    }

    // Calcula el número de espacios vacíos al comienzo de la cuadrícula del calendario para el mes actual. Utiliza la clase Calendar para obtener el día de la semana del primer día del mes y le resta 1.
    fun nGetDummySpaces(): Int {
        val calendario = Calendar.getInstance()
        calendario.set(this.calendarDates.calendarYear.toInt(), this.calendarDates.calendarMonthDate.toInt() - 1, 1)
        return calendario.get(Calendar.DAY_OF_WEEK) - 1
    }

    fun nGetCurrentDate(): Calendar {
        return Calendar.getInstance(Locale.ENGLISH)
    }

    fun setFirstDayOfNextMonth(day: Int) {
        this.firstDayOfNextMonth = day
    }

    fun getFirstDayOfNextMonth(): Int {
        return firstDayOfNextMonth
    }

    fun setCurrentDateSelected(selectedDate: Date?) {
        this.currentDateSelected = selectedDate
    }

    fun getCurrentDateSelected(): Date? {
        return currentDateSelected
    }

    fun setPositionUpdateAdapter(position: Int) {
        this.position = position
    }
    fun getPositionUpdateAdapter(): Int? {
        return position
    }

    /** Calendario Simplificado */

    fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    fun isSameDay(calendar1: Date, calendar2: Date): Boolean {
        val firstCalendar = Calendar.getInstance()
        val secondCalendar = Calendar.getInstance()
        firstCalendar.time = calendar1
        secondCalendar.time = calendar2
        return firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR) &&
                firstCalendar.get(Calendar.MONTH) == secondCalendar.get(Calendar.MONTH) &&
                firstCalendar.get(Calendar.DAY_OF_MONTH) == secondCalendar.get(Calendar.DAY_OF_MONTH)
    }

    fun calculateWeeksCount(firstDayOfWeek: Int, maxDaysInMonth: Int): Int {
        return when (firstDayOfWeek) {
            0 -> ceil(maxDaysInMonth.toFloat() / 7).toInt()
            else -> ceil((maxDaysInMonth.toFloat() - (firstDayOfWeek - 1)) / 7).toInt()
        }
    }

    fun calculateItemWidth(context: Context): Int {
        val screenWidth = getScreenWidth(context)
        return (screenWidth / 7).toInt() + 5
    }

    private fun getScreenWidth(context: Context): Float {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels.toFloat()
    }
}