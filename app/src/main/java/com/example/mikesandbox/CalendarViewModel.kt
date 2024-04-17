package com.example.mikesandbox

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mikesandbox.chico.CalendarDateModel
import java.util.Calendar

class CalendarViewModel : ViewModel() {
    val selectedDate: MutableLiveData<CalendarDateModel> = MutableLiveData()
    val monthlyCalendar: MutableLiveData<List<CalendarDateModel>> = MutableLiveData()
    val weeklyCalendar: MutableLiveData<List<List<CalendarDateModel>>> = MutableLiveData()

    fun fillCalendars(month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val monthlyCalendarDays = mutableListOf<CalendarDateModel>()
        val weeklyCalendarDays = mutableListOf<List<CalendarDateModel>>()
        var weekDays = mutableListOf<CalendarDateModel>()

        for (i in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val day = CalendarDateModel(calendar.time)

            monthlyCalendarDays.add(day)
            weekDays.add(day)

            if (weekDays.size == 7 || i == daysInMonth) {
                weeklyCalendarDays.add(weekDays)
                weekDays = mutableListOf()
            }
        }

        monthlyCalendar.postValue(monthlyCalendarDays)
        weeklyCalendar.postValue(weeklyCalendarDays)
    }
}