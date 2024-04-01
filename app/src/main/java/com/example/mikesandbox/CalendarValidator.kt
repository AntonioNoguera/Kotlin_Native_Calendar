package com.example.mikesandbox

import android.util.Log

class CalendarValidator(private var dateReceived: CalendarHelper) {
    //Aka month data set generator :
    private val offDays = dateReceived.nGetDisabledDates()

    fun getModel (): ArrayList<DayModel> {
        val monthModeled = arrayListOf<DayModel>()
        val headers = arrayListOf<String>("dom","lun","mar","mier","jue","vie","sab")

        for(head in headers){
            monthModeled.add(DayModel(head, dateReceived.nGetMonth(),dateReceived.nGetYear(), DayModel.Status_Next))
        }

        for (i in 1 .. dateReceived.nGetDummySpaces()){
            monthModeled.add(DayModel(" ", dateReceived.nGetMonth(),dateReceived.nGetYear(), DayModel.Status_Passed))
        }

        for (i in 1 .. dateReceived.nGetNumberOfDays()){
            if(i<dateReceived.nGetDate().day.toInt()){
                monthModeled.add(DayModel(i.toString(), dateReceived.nGetMonth(), dateReceived.nGetYear(), DayModel.Status_Passed))
            }else{
                if(!offDays.contains(i)){
                    monthModeled.add(DayModel(i.toString(), dateReceived.nGetMonth(),dateReceived.nGetYear()))
                }else{
                    monthModeled.add(DayModel(i.toString(), dateReceived.nGetMonth(),dateReceived.nGetYear(), DayModel.Status_Disabled))
                }

            }
        }
        return monthModeled
    }
}