package com.partos.todoapp.adapters.pager

import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.partos.flashback.db.DataBaseHelper
import com.partos.todoapp.R
import com.partos.todoapp.adapters.MarginItemDecoration
import com.partos.todoapp.adapters.recycler.CurrentRentsRecyclerViewAdapter
import com.partos.todoapp.adapters.recycler.ToDoRecyclerViewAdapter
import com.partos.todoapp.calendar.CalendarHelper
import com.partos.todoapp.models.ToDo
import kotlinx.android.synthetic.main.view_pager_cell.view.*

class CurrentRentsViewPagerAdapter : PagerAdapter {

    var context: Context

    //    var recyclerViews: Array<RecyclerView>
    lateinit var inflater: LayoutInflater

    constructor(context: Context) : super() {
        this.context = context
//        this.recyclerViews = recyclerViews
    }


    //    override fun getCount(): Int = recyclerViews.size
    override fun getCount(): Int = 2

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as ConstraintLayout

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var recyclerView: RecyclerView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.view_pager_cell, container, false)
        recyclerView = view.month_recycler_view

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager

        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )

        val calendarHelper = CalendarHelper(context)
        val constraintTodo = view.findViewById(R.id.main_constraint_todo) as ConstraintLayout
        val constraintCalendar = view.findViewById(R.id.main_constraint_calendar) as ConstraintLayout

        if (position == 0) {
            constraintTodo.visibility = View.VISIBLE
            constraintCalendar.visibility = View.GONE
            val todoRecycler = view.findViewById<RecyclerView>(R.id.main_todo_recycler_view)
            val db = DataBaseHelper(context)
            val currentDate = Calendar.getInstance()
            val date = db.getDate(
                currentDate.get(Calendar.DAY_OF_MONTH),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.YEAR)
            )
            val layoutManager = LinearLayoutManager(context)
            todoRecycler.layoutManager = layoutManager
            todoRecycler.addItemDecoration(MarginItemDecoration(12))
            val list = db.getToDoList(date.id)
            list.add(ToDo(-1, 0, "no", 0))
            todoRecycler.adapter = ToDoRecyclerViewAdapter(list)
        } else if (position == 1) {
            constraintTodo.visibility = View.GONE
            constraintCalendar.visibility = View.VISIBLE
            recyclerView.adapter =
                CurrentRentsRecyclerViewAdapter(calendarHelper.getDatesList())
        }

        container!!.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as ConstraintLayout)
    }
}