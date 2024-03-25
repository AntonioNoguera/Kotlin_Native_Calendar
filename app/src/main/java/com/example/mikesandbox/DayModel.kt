package com.example.mikesandbox

class DayModel(dayConstructor:String,monthConstructo:String,statusConstructor: Int = 0) {
    var day: String = ""
    var month: String = ""
    var status: Int = 0
    var id:Long = 0

    init {
        day = dayConstructor
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