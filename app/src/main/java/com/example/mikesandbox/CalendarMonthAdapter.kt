package com.example.mikesandbox

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarMonthAdapter(private val context: Context, private val monthSize:Int,private val dataset: ArrayList<ArrayList<DayModel>>,private val listenerMonth:Listener): RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder>() {

    val publicStub = arrayListOf<String>("Enero","Febrero","Marzo")

    val dataSeted = arrayListOf<DayModel>()

    private var publicLastSelectedHolder: calendarDayAdapter.ViewHolder? = null;

    interface Listener{
        fun dateSelectedMonth(selectedDate :String)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val monthText: TextView = view.findViewById(R.id.month_text_view)
        val yearText: TextView = view.findViewById(R.id.day_text_view)
        val monthRV : RecyclerView = view.findViewById(R.id.month_rv)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.detailed_calendar_month, parent, false)
        Log.d("Vista Recyclada","Created ViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarMonthAdapter.ViewHolder, position: Int) {
        val monthData = dataset[position]
        val itemMonth = monthData[0].month


        // Hace referencia a la posicion del mes que se habia seleccionado previamente
        val monthPosition = 0

        holder.monthText.text = itemMonth
        holder.yearText.text = monthData[0].year

        // número de columnas en el grid
        val spanCount = 7

        holder.monthRV.layoutManager = object: GridLayoutManager(context, spanCount){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        val adapter = calendarDayAdapter(context,dataset[position],
           listener =  object: calendarDayAdapter.Listener {

               override fun executeSelection(
                   selectedItem: Int,
                   holder: calendarDayAdapter.ViewHolder,
                   item: DayModel
               ) {
                   executeDaySelection(selectedItem,holder,item)
               }

            }
        )
        holder.monthRV.adapter = adapter
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private var selectedDay:DayModel? = null

    private fun executeDaySelection(selectedItem:Int, holder: calendarDayAdapter.ViewHolder, actualItem: DayModel){

        if(selectedDay != null){
            if(selectedDay!!.month != actualItem.month || actualItem.day != selectedDay!!.day){
                selectedOperation(calendarDayAdapter.UNSELECT,holder,actualItem)
            }
        }
        selectedOperation(calendarDayAdapter.SELECT,holder,actualItem)

        publicLastSelectedHolder = holder
        selectedDay = DayModel(actualItem.day,actualItem.month,actualItem.year)
    }

    private fun selectedOperation(typeOperation : Int, holder: calendarDayAdapter.ViewHolder, actualItem:DayModel) {
        if(typeOperation == calendarDayAdapter.SELECT){
            holder.textView.isSelected = true
        }else{
            publicLastSelectedHolder!!.textView.isSelected = false
        }
    }

}