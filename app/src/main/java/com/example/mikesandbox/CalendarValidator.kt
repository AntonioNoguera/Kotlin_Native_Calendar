package com.example.mikesandbox

import android.util.Log
import com.example.mikesandbox.chico.CalendarDateModel
import java.util.Calendar

class CalendarValidator(private var dateReceived: CalendarHelper) {
    //Aka month data set generator :
    private val offDays = dateReceived.nGetDisabledDates()

    /** Calendario Mensual */
    fun getModel (monthCalendar: Calendar): ArrayList<CalendarDateModel> {
        val monthModeled = arrayListOf<CalendarDateModel>()
        val headers = arrayListOf("dom","lun","mar","mier","jue","vie","sab")

        for(head in headers){
            monthModeled.add(
                CalendarDateModel(day = head, status = CalendarDateModel.Status_Next)
            )
        }


        for (i in 1 ..dateReceived.nGetDummySpaces()){
            monthModeled.add(
                CalendarDateModel(day = "", status = CalendarDateModel.Status_Passed)
            )
        }

        // Itera desde el día uno al numero de dias del mes actual
        for (i in 1 ..dateReceived.nGetNumberOfDays()){
            monthCalendar.set(Calendar.DAY_OF_MONTH, i)
            if(i < dateReceived.nGetDate().calendarDate.toInt()){
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
            dateReceived.setFirstDayOfNextMonth(1)

            // Si la semana actual tiene menos de 7 días y se deben mostrar los días del siguiente mes
            if (weekDays.size < 7) {
                val nextMonth = monthCalendar.clone() as Calendar
                nextMonth.add(Calendar.MONTH, 1)
                val daysNextMonth = generateWeekDays(nextMonth, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH), 1, 1, (7 - weekDays.size))
                dateReceived.setFirstDayOfNextMonth(8 - weekDays.size)
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
                val isToday = dateReceived.isSameDay(monthCalendar, dateReceived.nGetCurrentDate())

                // Si está habilitado
                if (!dateReceived.nGetDisabledDates().contains(currentDay)) {
                    weekDays.add(CalendarDateModel(data = monthCalendar.time, today = isToday))
                } else {
                    weekDays.add(CalendarDateModel(data = monthCalendar.time, today = isToday, status = CalendarDateModel.Status_Disabled))
                }
            }
        }

        return weekDays
    }
}