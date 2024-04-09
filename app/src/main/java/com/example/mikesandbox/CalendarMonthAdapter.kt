package com.example.mikesandbox

import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import java.time.Month

class CalendarMonthAdapter(private val context: Context, private val dataset: ArrayList<MonthModel>,private val listenerMonth:Listener): RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder>() {

    val MonthArray = arrayListOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")
    private var publicLastSelectedHolder: calendarDayAdapter.ViewHolder? = null;
    private var actualItem:Int = 0;

    interface Listener{
        fun dateSelectedMonth(selectedDate :String)
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
        val itemMonth = MonthArray[monthData.month]

        // Hace referencia a la posicion del mes que se habia seleccionado previamente
        val monthPosition = 0

        holder.monthText.text = itemMonth

        holder.yearText.text = "2023"

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
            //initialPrefetchItemCount = dataset.size
            isItemPrefetchEnabled = true
        }

        holder.monthRV.layoutManager = holderLayout

        val adapter = calendarDayAdapter(context,monthData.days,
            listener =  object: calendarDayAdapter.Listener {

                override fun executeSelection( holder: calendarDayAdapter.ViewHolder, item: DayModel ) {
                    executeDaySelection(holder,item)
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

    private var selectedDay:DayModel? = null

    private fun executeDaySelection(holder: calendarDayAdapter.ViewHolder, actualItem: DayModel){

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