package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.mikesandbox.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.Month
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val helper = CalendarHelper
        val validator = CalendarValidator(helper)
        val modData: ArrayList<ArrayList<DayModel>> = arrayListOf()

        val mockDays = arrayListOf(14,24)

        //Debe de haber un endpoint que de la fecha y hora actual
        //Implementacion temporal
        //Calendar model

        val calendarModel : ArrayList<MonthModel> = arrayListOf()

        val calen = Calendar.getInstance()
        var todayModel = DayModel(
                                    dayConstructor = calen.get(Calendar.DAY_OF_MONTH).toString(),
                                    monthConstructor = calen.get(Calendar.MONTH),
                                    yearConstructor = calen.get(Calendar.YEAR)
                                )

        //The iterator that makes the month adapter
        for(i in 0..30){

            todayModel.day = if (i==0) todayModel.day else 1.toString()

            val isOnScreen = i<=1

            helper.nSetDate(todayModel)
            helper.nSetDisabledDates(mockDays)

            calendarModel.add(MonthModel(monthConstructor = todayModel.month ,daysConstructor =  validator.getModel(),isOnScreen))

            todayModel = helper.nAddMonth(todayModel)
        }

        val adapter = CalendarMonthAdapter(this, calendarModel, listenerMonth = object: CalendarMonthAdapter.Listener{
            override fun dateSelectedMonth(selectedDate: String) {
                Toast.makeText(this@MainActivity, selectedDate,Toast.LENGTH_SHORT).show()
            }
        })

        //Snapper for the monthsAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.myRecyclerView)

        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.myRecyclerView.layoutManager = linearLayoutManager

        linearLayoutManager.apply {
            initialPrefetchItemCount = 5
            isItemPrefetchEnabled = true
        }

        binding.myRecyclerView.adapter = adapter

        binding.myRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager
                    val snapView = pagerSnapHelper.findSnapView(layoutManager)
                    snapView?.let {
                        val pos = recyclerView.getChildAdapterPosition(it)
                        // pos es la posición del elemento que se muestra actualmente
                        //Toast.makeText(this@MainActivity, "Current visible item position: $pos",Toast.LENGTH_SHORT).show()
                        adapter.memberVisible(pos)
                    }
                }
            }
        })
    }
}