package com.example.mikesandbox.grande

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.R
import com.example.mikesandbox.chico.CalendarDateModel

class CalendarMonthAdapter(private val context: Context, private val dataset: ArrayList<ArrayList<CalendarDateModel>>, private val listenerMonth: Listener): RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder>() {

    val MonthArray = arrayListOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")
    private var publicLastSelectedHolder: CalendarDayAdapter.ViewHolder? = null

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
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monthData = dataset[position]
        val itemMonth = monthData[15].calendarMonth.replaceFirstChar { it.uppercase() }
        val itemYear = monthData[15].calendarYear

        // Hace referencia a la posicion del mes que se habia seleccionado previamente
        val monthPosition = 0

        holder.monthText.text = itemMonth
        holder.yearText.text = itemYear

        // número de columnas en el grid
        val spanCount = 7

        val holderLayout = object: GridLayoutManager(context, spanCount){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        holderLayout.apply {
            initialPrefetchItemCount = 45
            isItemPrefetchEnabled = true
        }

        holder.monthRV.layoutManager = holderLayout

        val adapter = CalendarDayAdapter(context, dataset[position],
            listener = object: CalendarDayAdapter.Listener {

                override fun executeSelection(
                    holder: CalendarDayAdapter.ViewHolder,
                    item: CalendarDateModel
                ) {
                    executeDaySelection(holder,item)
                    println("/**${item.calendarMonth}")
                }

            }
        )
        holder.monthRV.adapter = adapter
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private var selectedDay: CalendarDateModel? = null

    private fun executeDaySelection(holder: CalendarDayAdapter.ViewHolder, actualItem: CalendarDateModel){

        if(selectedDay != null){
            if(selectedDay!!.calendarMonthDate.toInt() - 1 != actualItem.calendarMonthDate.toInt() || actualItem.day != selectedDay!!.day){
                selectedOperation(CalendarDayAdapter.UNSELECT,holder,actualItem)
            }
        }
        selectedOperation(CalendarDayAdapter.SELECT,holder,actualItem)

        publicLastSelectedHolder = holder
        selectedDay = CalendarDateModel(actualItem.data)
    }

    private fun selectedOperation(typeOperation : Int, holder: CalendarDayAdapter.ViewHolder, actualItem: CalendarDateModel) {
        if(typeOperation == CalendarDayAdapter.SELECT){
            holder.textView.isSelected = true
        }else{
            publicLastSelectedHolder!!.textView.isSelected = false
        }
    }

}