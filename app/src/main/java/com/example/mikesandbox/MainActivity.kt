package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

import com.example.mikesandbox.databinding.ActivityMainBinding
import java.time.LocalDate
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

        //Debe de haber un endpoint que de la fecha y hora actual
        //Implementacion temporal
        val calen = Calendar.getInstance()
        var todayModel = DayModel(
                                    dayConstructor = calen.get(Calendar.DAY_OF_MONTH).toString(),
                                    monthConstructor = calen.get(Calendar.MONTH),
                                    yearConstructor = calen.get(Calendar.YEAR)
                                )

        //The iterator that makes the month adapter
        for(i in 0..20){
            todayModel.day = if (i==0) todayModel.day else 1.toString()

            helper.nSetDate(todayModel)
            modData.add(validator.getModel())

            todayModel = helper.nAddMonth(todayModel)
        }

        val adapter = CalendarMonthAdapter(this, modData, listenerMonth = object: CalendarMonthAdapter.Listener{
            override fun dateSelectedMonth(selectedDate: String) {
                Log.d("Create Member",selectedDate)
                Toast.makeText(this@MainActivity, selectedDate,Toast.LENGTH_SHORT).show()
            }
        })

        //Snapper for the monthsAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.myRecyclerView)

        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.myRecyclerView.layoutManager = linearLayoutManager

        linearLayoutManager.apply {
            initialPrefetchItemCount = 12
            isItemPrefetchEnabled = true
        }

        binding.myRecyclerView.adapter = adapter
    }
}