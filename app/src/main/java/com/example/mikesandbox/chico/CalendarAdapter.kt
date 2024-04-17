package com.example.mikesandbox.chico

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.R

class CalendarAdapter(private val context: Context, private val days: List<CalendarDateModel>, private val widthLinear: Int,
                      private val onClick: (holder: CalendarViewHolder, actualItem: CalendarDateModel) -> Unit): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.date_layout, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = days[position]

        // Ajustar a la pantalla solo 7 días
        val layoutParams = holder.calendarDate.layoutParams as LinearLayout.LayoutParams
        layoutParams.gravity = Gravity.CENTER
        holder.linear.layoutParams.width = (widthLinear - 7.toPx()) // Convert to int for layout params


        holder.calendarDate.text = day.calendarDate

        holder.calendarDay.text = day.calendarDay.takeIf { !day.today } ?: "hoy"

        holder.linear.setOnClickListener {
            onClick(holder, day)
        }

        when(day.status){

            CalendarDateModel.Status_Disabled -> {
                holder.linear.isEnabled = false
                holder.calendarDate.setBackgroundResource(R.drawable.calendar_day_disabled_state)
                holder.calendarDay.setTextColor(ContextCompat.getColor(context, R.color.disabledPrimary))
                holder.calendarDate.setTextColor(ContextCompat.getColor(context, R.color.pink_ef))
            }

            else -> {
                //for the headers
                holder.calendarDate.setBackgroundResource(R.drawable.background_calendar_day)
                holder.calendarDate.setTextColor(
                    ContextCompat.getColorStateList(context,
                        R.drawable.fontcolor_calendar_day
                    ))
            }
        }
    }


    private fun Int.toPx(): Int {
        val metrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
    }

    class CalendarViewHolder(view: View): RecyclerView.ViewHolder(view) {
        internal val linear = itemView.findViewById<LinearLayout>(R.id.linear_calendar)
        internal val calendarDay = itemView.findViewById<TextView>(R.id.tv_calendar_day)
        internal val calendarDate = itemView.findViewById<TextView>(R.id.tv_calendar_date)
    }

}