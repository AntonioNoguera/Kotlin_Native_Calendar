package com.example.mikesandbox

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class calendarDayAdapter(private val context: Context, private val dataSet: ArrayList<DayModel>, private var selectedDay:Int? = null,private val listener: Listener) : RecyclerView.Adapter<calendarDayAdapter.ViewHolder>() {

    interface Listener {
        fun executeSelection(selectedItem:Int ,holder: ViewHolder,actualItem: DayModel)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.calendar_text_holder)
        val dayContainer: LinearLayoutCompat = view.findViewById(R.id.calendar_day_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detailed_calendar_atom_day, parent, false)
        return ViewHolder(view)
    }

    //Less pression on the aspect related to the override of the animation
    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val actualItem = dataSet[position]

        holder.textView.text = actualItem.day

        when(actualItem.status){

            DayModel.Status_Disabled -> {
                holder.textView.isEnabled = false
                holder.textView.setBackgroundResource(R.drawable.calendar_day_disabled_state)
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.pink_ef))
            }

            DayModel.Status_Passed -> {
                holder.textView.isEnabled = false
                holder.textView.setBackgroundResource(R.drawable.calendary_day_default_state)
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.disabledPrimary))
            }

            DayModel.Status_Next -> {
                holder.textView.setBackgroundResource(R.drawable.background_calendar_day)
                holder.textView.setTextColor(ContextCompat.getColorStateList(context, R.drawable.fontcolor_calendar_day))
            }

            else -> {
                //for the headers
                holder.textView.setBackgroundResource(R.drawable.background_calendar_day)
                holder.textView.setTextColor(ContextCompat.getColorStateList(context, R.drawable.fontcolor_calendar_day))
            }
        }

        holder.dayContainer.setOnClickListener {
            if(actualItem.status == DayModel.Status_Active){
                listener.executeSelection(position,holder,actualItem)
            }
        }

    }

    override fun getItemCount() = dataSet.size

    companion object{
        const val SELECT = 0
        const val UNSELECT = 1
    }
}