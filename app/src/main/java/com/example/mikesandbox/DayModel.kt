package com.example.mikesandbox

data class DayModel(var day: String, var month: Int, var year: Int, var status: Int = 0) {
    companion object {
        const val Status_Active = 0
        const val Status_Disabled = 1
        const val Status_Selected = 2
        const val Status_Passed = 3
        const val Status_Next = 4
    }
}