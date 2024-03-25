package com.example.mikesandbox

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarMonthAdapter(private val context: Context, private val month:String, private val year:String, private val monthSize:Int,private val dataset: ArrayList<DayModel>,private val listenerMonth:Listener): RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder>() {

    val publicStub = arrayListOf<String>("Enero","Febrero","Marzo")
    val dataSeted = arrayListOf<DayModel>()

    val selectedDay = ""

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

        val itemMonth = publicStub[position]
        val startTime = System.nanoTime()

        holder.monthText.text = itemMonth
        holder.yearText.text = "2024"

        val spanCount = 7 // número de columnas en el grid

        holder.monthRV.layoutManager = object: GridLayoutManager(context, spanCount){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        val adapter = calendarDayAdapter(context,dataset,
           listener =  object: calendarDayAdapter.Listener {
               override fun dateSelected(item: DayModel, index: Int) {
                   //Notificar al anterior mes que debe de deseleccionar
                   val selected:String = item.day.toString() + itemMonth
                   listenerMonth.dateSelectedMonth(selected)
               }

            }
        )
        holder.monthRV.adapter = adapter
        val endTime = System.nanoTime()
        Log.d("Vista Recyclada","onBindViewHolder $itemMonth ${(endTime - startTime) / 1_000_000}ms")

    }

    override fun onViewRecycled(holder: ViewHolder) {
        Log.d("Vista Recyclada","HOLDER RECYCLED ${holder.monthText.text}")
        super.onViewRecycled(holder)
        // Código para manejar la vista reciclada, por ejemplo, limpiar recursos.
    }

    override fun getItemCount(): Int {
        return monthSize
    }


}