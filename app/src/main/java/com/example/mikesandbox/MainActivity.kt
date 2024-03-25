package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
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



        var validator : CalendarValidator = CalendarValidator(helper)

        val today = LocalDate.now()
        helper.setDate(today)

        var modData = validator.getModel()

        val adapter = CalendarMonthAdapter(this,"Marzo","2023",12,modData, listenerMonth = object: CalendarMonthAdapter.Listener{
            override fun dateSelectedMonth(selectedDate: String) {
                Log.d("Create Member",selectedDate)
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

        // Crear y establecer el adaptador para el RecyclerView
        binding.myRecyclerView.adapter = adapter
    }
}