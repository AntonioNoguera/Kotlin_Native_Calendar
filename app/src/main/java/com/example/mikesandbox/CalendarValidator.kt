package com.example.mikesandbox

class CalendarValidator(private var dateReceived: CalendarHelper) {
    //Aka month data set generator :P
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
                monthModeled.add(DayModel(i.toString(), dateReceived.nGetMonth(),dateReceived.nGetYear()))
            }

        }
        return monthModeled
    }
}