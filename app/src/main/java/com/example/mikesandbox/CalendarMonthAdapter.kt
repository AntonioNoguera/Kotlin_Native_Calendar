package com.example.mikesandbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarMonthAdapter(private val context: Context, private val month:String, private val year:String, private val monthSize:Int,private val dataset: ArrayList<DayModel>): RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder>() {

    val publicStub = arrayListOf<String>("Enero","Febero","Marzo")
    val dataSeted = arrayListOf<DayModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val monthText: TextView = view.findViewById(R.id.month_text_view)
        val yearText: TextView = view.findViewById(R.id.day_text_view)
        val monthRV : RecyclerView = view.findViewById(R.id.month_rv)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): CalendarMonthAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detailed_calendar_month, parent, false)
        return CalendarMonthAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarMonthAdapter.ViewHolder, position: Int) {
        val item = publicStub[position]

        holder.monthText.text = item
        holder.yearText.text = "2034"

        val spanCount = 7 // número de columnas en el grid
        val spacingH = 10// espaciado entre elementos en píxeles
        val spacingV = 0 // espaciado entre elementos en píxeles
        val includeEdge = true // incluir espaciado en los bordes del grid

        holder.monthRV.addItemDecoration(GridSpacingItemDecoration(context,spanCount,spacingH, spacingV, includeEdge))
        holder.monthRV.layoutManager = object: GridLayoutManager(context, spanCount){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        val adapter = calendarDayAdapter(context,dataset)
        holder.monthRV.adapter = adapter
    }

    override fun getItemCount(): Int {
        return monthSize
    }


}