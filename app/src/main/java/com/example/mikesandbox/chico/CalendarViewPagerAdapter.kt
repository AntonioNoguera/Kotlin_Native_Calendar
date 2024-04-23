package com.example.mikesandbox.chico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.CalendarHelper
import com.example.mikesandbox.R
import com.example.mikesandbox.grande.CalendarDayAdapter

class CalendarViewPagerAdapter(private val context: Context, private var daysOfMonth: ArrayList<List<CalendarDateModel>>, private val widthLinear: Int,
                               private val onClick: (holder: CalendarAdapter.CalendarViewHolder, actualItem: CalendarDateModel) -> Unit) : RecyclerView.Adapter<CalendarViewPagerAdapter.ViewHolder>() {

    private var publicLastSelectedHolder: CalendarAdapter.CalendarViewHolder? = null
    private var adapterCalendar: CalendarAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_view_pager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapterCalendar = CalendarAdapter(context, daysOfMonth[position], widthLinear, onClick =  { holder, currentDay, position1 ->
            executeDaySelection(holder, currentDay, position1)
            onClick(holder, currentDay)
        })

        holder.rcv.apply {
            layoutManager = LinearLayoutManager(holder.rcv.context, RecyclerView.HORIZONTAL, false)
            this.adapter = adapterCalendar
        }

    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }



    private fun executeDaySelection(holder: CalendarAdapter.CalendarViewHolder, actualItem: CalendarDateModel, position: Int) {
        CalendarHelper.setCurrentDateSelected(actualItem.data)
        notifyDataSetChanged()

    }


    fun updateData(calendarList: List<List<CalendarDateModel>>) {
        daysOfMonth.addAll(calendarList)
        notifyItemRangeInserted(daysOfMonth.lastIndex, calendarList.size)
    }

    fun updateSelectedDate(position: Int) {
        notifyItemChanged(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rcv: RecyclerView = view.findViewById(R.id.rcv_calendar)
    }

    companion object {
        const val SELECT = 0
        const val UNSELECT = 1
    }
}