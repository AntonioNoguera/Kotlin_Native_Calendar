package com.example.mikesandbox

class MonthModel (monthConstructor:Int, daysConstructor : ArrayList<DayModel>, isOnScreen:Boolean) {
    var month: Int = 0
    var days: ArrayList<DayModel> = arrayListOf()
    var isOnScreen = false
    init {
        this.month = monthConstructor
        this.days.addAll(daysConstructor)
        this.isOnScreen = isOnScreen
    }
}