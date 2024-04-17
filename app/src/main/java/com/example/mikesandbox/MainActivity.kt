package com.example.mikesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mikesandbox.chico.CalendarDateModel
import com.example.mikesandbox.chico.CalendarViewPagerAdapter
import com.example.mikesandbox.databinding.ActivityMainBinding
import com.example.mikesandbox.grande.CalendarMonthAdapter
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var calendarViewPagerAdapter: CalendarViewPagerAdapter
    private val helper = CalendarHelper
    private val validator = CalendarValidator(helper)

    private val calendar = Calendar.getInstance()
    private var calendarDataList = mutableListOf<List<CalendarDateModel>>()

    private val calendarModel: ArrayList<MonthModel> = arrayListOf()
    private val mockDays = arrayListOf(14, 24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpAdapter()
        setUpCalendar(totalMonths = 20)
        setupRecyclerView()

    }

    /** Calendario Grande*/
    private fun setupRecyclerView() {
        binding.myRecyclerView.apply {
            val adapter = CalendarMonthAdapter(this@MainActivity, calendarModel, listenerMonth = object : CalendarMonthAdapter.Listener {
                override fun dateSelectedMonth(selectedDate: String) {
                    Toast.makeText(this@MainActivity, selectedDate, Toast.LENGTH_SHORT).show()
                }
            })

            //Snapper for the monthsAdapter
            PagerSnapHelper().attachToRecyclerView(this)

            val linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false).apply {
                initialPrefetchItemCount = 5
                isItemPrefetchEnabled = true
            }
            layoutManager = linearLayoutManager
            this.adapter = adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val layoutManager = recyclerView.layoutManager
                        val snapView = PagerSnapHelper().findSnapView(layoutManager)
                        snapView?.let {
                            val pos = recyclerView.getChildAdapterPosition(it)
                            // pos es la posición del elemento que se muestra actualmente
                            adapter.memberVisible(pos)
                        }
                    }
                }
            })

        }
    }

    private fun monthlyCalendar(todayModel: CalendarDateModel, calendarMonth: Calendar, month: Int) {
        //The iterator that makes the month adapter
        val setDate = if (month == 0) todayModel.calendarDate.toInt() else 1
        calendarMonth.set(Calendar.DAY_OF_MONTH, setDate)
        val isOnScreen = month <= 1

        helper.nSetDate(calendarMonth)
        helper.nSetDisabledDates(mockDays)

        calendarModel.add(MonthModel(month = todayModel.calendarMonthDate.toInt() - 1 , days =  validator.getModel(calendarMonth), isOnScreen))

        helper.nAddMonth(calendarMonth)
    }

    /** Calendario Chico */
    private fun setUpAdapter() {
        val itemWidth = helper.calculateItemWidth(this@MainActivity)

        // initialize CalendarViewPagerAdapter
        calendarViewPagerAdapter = CalendarViewPagerAdapter(this@MainActivity, arrayListOf(), itemWidth, onClick = { a, b ->

        })

        binding.viewPagerCalendar.apply {
            adapter = calendarViewPagerAdapter
            offscreenPageLimit = 1
        }

    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar(totalMonths: Int) {
        //Debe de haber un endpoint que de la fecha y hora actual
        //Implementacion temporal
        val currentMonthCalendar = calendar.clone() as Calendar
        val calendarMonth = calendar.clone() as Calendar
        val todayModel = CalendarDateModel(data = calendarMonth.time)
        val calendarList: MutableList<List<CalendarDateModel>> = mutableListOf()

        for (month in 0.. totalMonths) {
            val firstDayOfWeek = if (month == 0) currentMonthCalendar.get(Calendar.DAY_OF_MONTH) else helper.getFirstDayOfNextMonth()
            val maxDaysInMonth = currentMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val weeksCount = helper.calculateWeeksCount(firstDayOfWeek, maxDaysInMonth)

            // Calendario mensual
            monthlyCalendar(todayModel, calendarMonth, month)

            // Calendario semanal
            calendarList += validator.generateCalendarList(currentMonthCalendar, maxDaysInMonth, weeksCount, firstDayOfWeek)
            currentMonthCalendar.add(Calendar.MONTH, 1)
        }

        this.calendarDataList.addAll(calendarList)
        calendarViewPagerAdapter.updateData(calendarList)
    }
}