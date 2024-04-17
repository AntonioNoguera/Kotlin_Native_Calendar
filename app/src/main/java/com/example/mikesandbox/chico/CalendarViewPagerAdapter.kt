package com.example.mikesandbox.chico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.R

class CalendarViewPagerAdapter(private val context: Context, private var daysOfMonth: ArrayList<List<CalendarDateModel>>, private val widthLinear: Int,
                               private val onClick: (holder: CalendarAdapter.CalendarViewHolder, actualItem: CalendarDateModel) -> Unit) : RecyclerView.Adapter<CalendarViewPagerAdapter.ViewHolder>() {

    private var publicLastSelectedHolder: CalendarAdapter.CalendarViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_view_pager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adapterNames = CalendarAdapter(context, daysOfMonth[position], widthLinear, onClick =  { holder, currentDay ->
            executeDaySelection(holder, currentDay)
        })

        holder.rcv.apply {
            layoutManager = LinearLayoutManager(holder.rcv.context, RecyclerView.HORIZONTAL, false)
            adapter = adapterNames
        }

    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }


    private var selectedDay: CalendarDateModel? = null

    private fun executeDaySelection(holder: CalendarAdapter.CalendarViewHolder, actualItem: CalendarDateModel){
        if(selectedDay != null) {
            if (selectedDay!!.calendarMonthDate.toInt() - 1 != actualItem.calendarMonthDate.toInt() || actualItem.day != selectedDay!!.day) {
                selectedOperation(UNSELECT, holder)
            }
        }
        selectedOperation(SELECT, holder)

        publicLastSelectedHolder = holder
        selectedDay = CalendarDateModel(actualItem.data)
    }

    private fun selectedOperation(typeOperation: Int, holder: CalendarAdapter.CalendarViewHolder) {
        if (typeOperation == SELECT) {
            holder.calendarDate.isSelected = true
        } else {
            publicLastSelectedHolder!!.calendarDate.isSelected = false
        }
    }

    fun updateData(calendarList: List<List<CalendarDateModel>>) {
        daysOfMonth.addAll(calendarList)
        notifyItemRangeInserted(daysOfMonth.lastIndex, calendarList.size)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rcv: RecyclerView = view.findViewById(R.id.rcv_calendar)
    }

    companion object{
        const val SELECT = 0
        const val UNSELECT = 1
    }
}