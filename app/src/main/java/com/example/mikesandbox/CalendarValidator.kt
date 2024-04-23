package com.example.mikesandbox

import android.widget.Toast
import com.example.mikesandbox.chico.CalendarDateModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarValidator(private var helper: CalendarHelper) {
    //Aka month data set generator :
    private val offDays = helper.nGetDisabledDates()

    /** Calendario Mensual */
    fun getModel (monthCalendar: Calendar): ArrayList<CalendarDateModel> {
        val monthModeled = arrayListOf<CalendarDateModel>()
        val headers = arrayListOf("dom","lun","mar","mier","jue","vie","sab")

        for(head in headers){
            monthModeled.add(
                CalendarDateModel(day = head, status = CalendarDateModel.Status_Next)
            )
        }


        for (i in 1 ..helper.nGetDummySpaces()){
            monthModeled.add(
                CalendarDateModel(day = "", status = CalendarDateModel.Status_Passed)
            )
        }

        // Itera desde el día uno al numero de dias del mes actual
        for (i in 1 ..helper.nGetNumberOfDays()){
            monthCalendar.set(Calendar.DAY_OF_MONTH, i)
            if(i < helper.nGetDate().calendarDate.toInt()){
                // Si es anterior al dia de hoy
                monthModeled.add(CalendarDateModel(monthCalendar.time, status = CalendarDateModel.Status_Passed))
            } else{
                // Si está habilitado
                if (!offDays.contains(i)) {
                    monthModeled.add(CalendarDateModel(data = monthCalendar.time))
                } else {
                    monthModeled.add(CalendarDateModel(data = monthCalendar.time, status = CalendarDateModel.Status_Disabled))
                }

            }
        }
        return monthModeled
    }

    /** Calendario Semanal */
    fun generateCalendarList(monthCalendar: Calendar, maxDaysInMonth: Int, weeksCount: Int, firstDayOfWeek: Int): List<List<CalendarDateModel>> {
        val calendarList = mutableListOf<List<CalendarDateModel>>()
        var weekDays: List<CalendarDateModel>

        for (week in 1..weeksCount) {
            weekDays = generateWeekDays(monthCalendar, maxDaysInMonth, week, firstDayOfWeek)
            helper.setFirstDayOfNextMonth(1)

            // Si la semana actual tiene menos de 7 días y se deben mostrar los días del siguiente mes
            if (weekDays.size < 7) {
                val nextMonth = monthCalendar.clone() as Calendar
                nextMonth.add(Calendar.MONTH, 1)
                val daysNextMonth = generateWeekDays(nextMonth, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH), 1, 1, (7 - weekDays.size))
                helper.setFirstDayOfNextMonth(8 - weekDays.size)
                weekDays += daysNextMonth
            }

            calendarList.add(weekDays)
        }

        return calendarList
    }

    private fun generateWeekDays(monthCalendar: Calendar, maxDaysInMonth: Int, week: Int, firstDayOfWeek: Int, remainingDates: Int? = null): List<CalendarDateModel> {
        val weekDays = mutableListOf<CalendarDateModel>()

        for (day in firstDayOfWeek..(remainingDates ?: (firstDayOfWeek + 6))) {
            val currentDay = day + (week - 1) * 7
            if (currentDay <= maxDaysInMonth) {
                monthCalendar.set(Calendar.DAY_OF_MONTH, currentDay)
                val isToday = helper.isSameDay(monthCalendar, helper.nGetCurrentDate())

                // Si está habilitado
                if (!helper.nGetDisabledDates().contains(currentDay)) {
                    weekDays.add(CalendarDateModel(data = monthCalendar.time, today = isToday))
                } else {
                    weekDays.add(CalendarDateModel(data = monthCalendar.time, today = isToday, status = CalendarDateModel.Status_Disabled))
                }
            }
        }

        return weekDays
    }

    fun monthsRemainingCalculator(month: String, year: String): Int {
        var data = helper.nGetCurrentDate().time
        val calendarMonthDate = SimpleDateFormat("MM", Locale.getDefault()).format(data).toInt()
        val calendarYearDate = SimpleDateFormat("yyyy", Locale.getDefault()).format(data).toInt()
        var yearsDifference = year.toInt() - calendarYearDate

        return (month.toInt() - calendarMonthDate) + (yearsDifference * 12)
    }

    fun weeksRemainingCalculator(day: String, month: String, year: String): Int {
        val today = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        endDate.time = sdf.parse("$day-$month-$year")

        var days = daysBetweenDates(today, endDate)

        var weeks = days / 7

        return weeks
    }

    private fun daysBetweenDates(startDate: Calendar, endDate: Calendar): Int {
        startDate.set(Calendar.HOUR_OF_DAY, 0)
        startDate.set(Calendar.MINUTE, 0)
        startDate.set(Calendar.SECOND, 0)
        startDate.set(Calendar.MILLISECOND, 0)

        val diff = endDate.time.time - startDate.time.time

        return (diff / (24 * 60 * 60 * 1000)).toInt()
    }

}