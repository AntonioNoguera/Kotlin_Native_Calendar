package com.example.mikesandbox

import com.example.mikesandbox.chico.CalendarDateModel

data class MonthModel(
    var month: Int = 0,
    val days: ArrayList<CalendarDateModel> = arrayListOf(),
    var isOnScreen: Boolean = false
)