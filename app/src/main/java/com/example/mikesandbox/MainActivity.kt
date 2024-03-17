package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myDataset = listOf("dom","lun","mar","mie","jue","vie","sab","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")


        val spanCount = 7 // número de columnas en el grid
        val spacingH = 30 // espaciado entre elementos en píxeles
        val spacingV = 2 // espaciado entre elementos en píxeles
        val includeEdge = false // incluir espaciado en los bordes del grid

        val orderedLinearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.myRecyclerView.layoutManager = orderedLinearLayoutManager


        //binding.myRecyclerView.addItemDecoration(GridSpacingItemDecoration(this,spanCount,spacingH, spacingV, includeEdge))
        //binding.myRecyclerView.layoutManager = GridLayoutManager(this, spanCount)

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.myRecyclerView)

        val adapter = calendarDayAdapter(myDataset)
        binding.myRecyclerView.adapter = adapter


    }
}