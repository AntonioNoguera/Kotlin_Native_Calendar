package com.example.mikesandbox

import java.time.LocalDate

class CalendarValidator(private var dateReceived: CalendarHelper) {

    fun getModel (): ArrayList<DayModel> {
        val monthModeled = arrayListOf<DayModel>()
        val headers = arrayListOf<String>("dom","lun","mar","mier","jue","vie","sab")

        for(head in headers){
            monthModeled.add(DayModel(head, dateReceived.getMonthName(),DayModel.Status_Next))
        }

        for (i in 1 .. dateReceived.getDummySpaces()){
            monthModeled.add(DayModel(" ",dateReceived.getMonthName(),DayModel.Status_Passed))
        }

        for (i in 1 .. dateReceived.getNumberOfDays()){
            if(i<dateReceived.getDate().dayOfMonth){
                monthModeled.add(DayModel(i.toString(),dateReceived.getMonthName(),DayModel.Status_Passed))
            }else{
                monthModeled.add(DayModel(i.toString(),dateReceived.getMonthName()))
            }

        }
        return monthModeled
    }



}