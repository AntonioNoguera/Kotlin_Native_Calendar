package com.example.mikesandbox

class DayModel(dayConstructor: String, monthConstructor:String, yearConstructor: String, statusConstructor: Int = 0) {
    var day: String = ""
    var month: String = ""
    var year:String = ""
    var status: Int = 0

    init {
        day = dayConstructor
        month = monthConstructor
        year = yearConstructor
        status = statusConstructor
    }

    companion object {
        const val Status_Active = 0
        const val Status_Disabled = 1
        const val Status_Selected = 2
        const val Status_Passed = 3
        const val Status_Next = 4
    }
}