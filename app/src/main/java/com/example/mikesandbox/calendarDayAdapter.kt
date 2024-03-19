package com.example.mikesandbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class calendarDayAdapter(private val context: Context, private val dataSet: ArrayList<DayModel>, private var selectedDay:Int? = null) : RecyclerView.Adapter<calendarDayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.calendar_text_holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detailed_calendar_atom_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val actualItem = dataSet[position]

        holder.textView.text = actualItem.day


        when(actualItem.status){
            DayModel.Status_Selected -> {
                holder.textView.setBackgroundResource(R.drawable.calendar_day_selected_state)
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.pink_ef))
            }

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
                holder.textView.setBackgroundResource(R.drawable.calendary_day_default_state)
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.transWhite))
            }

            else -> {
                holder.textView.setBackgroundResource(R.drawable.calendary_day_default_state)
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

        holder.textView.setOnClickListener {

            if(actualItem.status == DayModel.Status_Active){
                executeSelection(position)
            }
        }
    }

    private fun executeSelection(selectedItem:Int){

        if(selectedDay != null && selectedItem != selectedDay){
            selectedOperation(selectedDay!!, UNSELECT)
        }
        selectedOperation(selectedItem, SELECT)

        selectedDay = selectedItem
    }

    private fun selectedOperation(indexOfDay : Int, typeOperation : Int) {
        if(typeOperation == SELECT){
            dataSet[indexOfDay].status = DayModel.Status_Selected
        }else{
            dataSet[indexOfDay].status = DayModel.Status_Active
        }

        notifyItemChanged(indexOfDay)
    }

    override fun getItemCount() = dataSet.size

    companion object{
        const val SELECT = 0
        const val UNSELECT = 1
    }
}