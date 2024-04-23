package com.example.mikesandbox.grande

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.CalendarHelper
import com.example.mikesandbox.MonthModel
import com.example.mikesandbox.R
import com.example.mikesandbox.chico.CalendarDateModel

class CalendarMonthAdapter(private val context: Context, private val dataset: ArrayList<MonthModel>, private val listenerMonth: Listener): RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder>() {

    private var adapter: CalendarDayAdapter? = null
    private var actualItem:Int = 0;


    interface Listener{
        fun dateSelectedMonth(selectedDate: CalendarDateModel)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val monthText: TextView = view.findViewById(R.id.month_text_view)
        val yearText: TextView = view.findViewById(R.id.day_text_view)
        val monthRV : RecyclerView = view.findViewById(R.id.month_rv)
        val shimmerRV : View = view.findViewById(R.id.month_holder)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detailed_calendar_month, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monthData = dataset[position]
        val itemMonth = monthData.days[15].calendarMonth
        val itemYear = monthData.days[15].calendarYear

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

        if(monthData.isOnScreen){
            holder.monthRV.visibility = View.VISIBLE
            holder.shimmerRV.visibility = View.GONE
        }else{
            holder.monthRV.visibility = View.GONE
            holder.shimmerRV.visibility = View.VISIBLE
        }

        holderLayout.apply {
            isItemPrefetchEnabled = true
        }

        holder.monthRV.layoutManager = holderLayout

        adapter = CalendarDayAdapter(context, monthData.days,
            listener = object: CalendarDayAdapter.Listener {
                override fun executeSelection(holder: CalendarDayAdapter.ViewHolder, item: CalendarDateModel, position: Int) {
                    executeDaySelection(holder,item, position)
                    listenerMonth.dateSelectedMonth(item)
                }

                override fun lastItemRendered() {
                    holder.monthRV.visibility=View.VISIBLE
                }

            }
        )
        holder.monthRV.adapter = adapter
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun memberVisible (position:Int){

        dataset[actualItem].isOnScreen = false
        notifyItemChanged(actualItem)

        dataset[position].isOnScreen = true
        actualItem = position
        notifyItemChanged(actualItem)
    }


    private fun executeDaySelection(holder: CalendarDayAdapter.ViewHolder, actualItem: CalendarDateModel, position: Int) {
        CalendarHelper.setCurrentDateSelected(actualItem.data)
        notifyDataSetChanged()
    }

    fun updateSelectedDate(position: Int) {
        notifyItemChanged(position)
    }

}