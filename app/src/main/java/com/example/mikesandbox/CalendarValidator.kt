package com.example.mikesandbox

class CalendarValidator(private var dateReceived: CalendarHelper) {
    //Aka month data set generator :P
    fun getModel (): ArrayList<DayModel> {
        val monthModeled = arrayListOf<DayModel>()
        val headers = arrayListOf<String>("dom","lun","mar","mier","jue","vie","sab")

        for(head in headers){
            monthModeled.add(DayModel(head, dateReceived.getMonthName(),dateReceived.getYear(),DayModel.Status_Next))
        }

        for (i in 1 .. dateReceived.getDummySpaces()){
            monthModeled.add(DayModel(" ",dateReceived.getMonthName(),dateReceived.getYear(),DayModel.Status_Passed))
        }

        for (i in 1 .. dateReceived.getNumberOfDays()){
            if(i<dateReceived.getDate().dayOfMonth){
                monthModeled.add(DayModel(i.toString(),dateReceived.getMonthName(),dateReceived.getYear(),DayModel.Status_Passed))
            }else{
                monthModeled.add(DayModel(i.toString(),dateReceived.getMonthName(),dateReceived.getYear()))
            }

        }
        return monthModeled
    }
}