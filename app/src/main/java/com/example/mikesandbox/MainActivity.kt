package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.mikesandbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myDataset = listOf("dom","lun","mar","mie","jue","vie","sab","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31")

        val modData = arrayListOf<DayModel>()

        for(item in myDataset){
            if(item == "1"){
                modData.add(DayModel("29",DayModel.Status_Passed))
                modData.add(DayModel("30",DayModel.Status_Passed))
                modData.add(DayModel("31",DayModel.Status_Disabled))
            }
            modData.add(DayModel(item))

        }

        modData.add(DayModel("1",DayModel.Status_Next))
        modData.add(DayModel("2",DayModel.Status_Next))
        modData.add(DayModel("3",DayModel.Status_Next))
        val adapter = CalendarMonthAdapter(this,"Marzo","2023",3,modData)

        //Snapper for the monthsAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.myRecyclerView)

        binding.myRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.myRecyclerView.adapter = adapter

    }
}