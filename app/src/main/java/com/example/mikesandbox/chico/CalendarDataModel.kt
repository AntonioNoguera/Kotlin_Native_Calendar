package com.example.mikesandbox.chico

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class CalendarDateModel(
    var data: Date? = null,
    var day: String = "",
    var today: Boolean = false,
    var isSelected: Boolean = true,
    var isOnScreen: Boolean = false,
    var status: Int = 0
) {

    val calendarDay: String
        get() = SimpleDateFormat("EE", Locale.getDefault()).format(data)

    val calendarMonth: String
        get() = SimpleDateFormat("MMMM", Locale.getDefault()).format(data)

    val calendarMonthDate: String
        get() = SimpleDateFormat("MM", Locale.getDefault()).format(data)

    val calendarYear: String
        get() = SimpleDateFormat("yyyy", Locale.getDefault()).format(data)

    val calendarDate: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = data
            return cal[Calendar.DAY_OF_MONTH].toString()
        }


    companion object {
        const val Status_Active = 0
        const val Status_Disabled = 1
        const val Status_Selected = 2
        const val Status_Passed = 3
        const val Status_Next = 4
    }
}