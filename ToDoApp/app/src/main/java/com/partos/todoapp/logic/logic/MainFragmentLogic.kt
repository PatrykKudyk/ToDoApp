package com.partos.todoapp.logic.logic

import android.content.Context
import android.icu.util.Calendar
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.partos.flashback.db.DataBaseHelper
import com.partos.todoapp.MyApp
import com.partos.todoapp.R
import com.partos.todoapp.adapters.pager.CurrentRentsViewPagerAdapter
import com.partos.todoapp.logic.listeners.MainFragmentListeners

class MainFragmentLogic {

    private lateinit var viewPager: ViewPager
    private lateinit var recyclerView: RecyclerView

    fun initFragment(rootView: View) {
        attachViews(rootView)
        handleDates(rootView.context)
        attachViewPager(rootView.context)
        Handler().postDelayed({
            recyclerView = rootView.findViewById(R.id.main_todo_recycler_view)
            MainFragmentListeners().initListeners(rootView, recyclerView)
        }, 1000)
    }

    private fun handleDates(context: Context) {
        val db = DataBaseHelper(context)
        val currentDate = Calendar.getInstance()
        if (db.getDateList().size == 0) {
            for (year in currentDate.get(Calendar.YEAR)..currentDate.get(Calendar.YEAR) + 2) {
                if (year == currentDate.get(Calendar.YEAR)) {
                    for (month in currentDate.get(Calendar.MONTH)..12) {
                        db.initMonth(month, year)
                    }
                } else {
                    for (month in 1..12) {
                        db.initMonth(month, year)
                    }
                }
            }
        } else {
            if (db.getMonth(1, currentDate.get(Calendar.YEAR)).size == 0) {
                for (month in 1..12) {
                    db.initMonth(month, currentDate.get(Calendar.YEAR))
                }
            }
            if (db.getMonth(1, currentDate.get(Calendar.YEAR) + 1).size == 0) {
                for (month in 1..12) {
                    db.initMonth(month, currentDate.get(Calendar.YEAR) + 1)
                }
            }
            if (db.getMonth(1, currentDate.get(Calendar.YEAR) + 2).size == 0) {
                for (month in 1..12) {
                    db.initMonth(month, currentDate.get(Calendar.YEAR) + 2)
                }
            }
        }
        val today = db.getDate(
            currentDate.get(Calendar.DAY_OF_MONTH),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.YEAR)
        )
        MyApp.dateId = today.id
    }

    private fun attachViewPager(context: Context) {
        viewPager.adapter = CurrentRentsViewPagerAdapter(context)
    }

//    private fun attachRecyclerView(context: Context) {
//        val db = DataBaseHelper(context)
//        val currentDate = Calendar.getInstance()
//        val date = db.getDate(
//            currentDate.get(Calendar.DAY_OF_MONTH),
//            currentDate.get(Calendar.MONTH),
//            currentDate.get(Calendar.YEAR)
//        )
//        val layoutManager = LinearLayoutManager(context)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.addItemDecoration(MarginItemDecoration(12))
//        val list = db.getToDoList(date.id)
//        list.add(ToDo(-1, 0, "no", 0))
//        recyclerView.adapter = ToDoRecyclerViewAdapter(list)
//    }

    private fun attachViews(rootView: View) {
        viewPager = rootView.findViewById(R.id.current_view_pager)
    }
}