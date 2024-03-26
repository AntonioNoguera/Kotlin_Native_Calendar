package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

import com.example.mikesandbox.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val helper = CalendarHelper

        val validator = CalendarValidator(helper)

        val modData: ArrayList<ArrayList<DayModel>> = arrayListOf()

        val thisDay = LocalDate.now()
        for(i in 0..20){
            val date = if (i==0) thisDay.dayOfMonth else 1
            val newMonth = thisDay.plusMonths(i.toLong())
            val today = LocalDate.of(newMonth.year,newMonth.monthValue, date)
            helper.setDate(today)
            modData.add(validator.getModel())

            Log.d("Generated Month Verification :","Month data size ${modData.size.toString()}")
        }

        val adapter = CalendarMonthAdapter(this,modData.size,modData, listenerMonth = object: CalendarMonthAdapter.Listener{
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