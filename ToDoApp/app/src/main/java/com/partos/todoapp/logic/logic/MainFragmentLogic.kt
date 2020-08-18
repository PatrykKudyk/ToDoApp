package com.partos.todoapp.logic.logic

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partos.todoapp.R
import com.partos.todoapp.adapters.MarginItemDecoration
import com.partos.todoapp.adapters.recycler.ToDoRecyclerViewAdapter
import com.partos.todoapp.models.ToDo

class MainFragmentLogic {

    private lateinit var recyclerView: RecyclerView

    fun initFragment(rootView: View) {
        attachViews(rootView)
        attachRecyclerView(rootView.context)
    }

    private fun attachRecyclerView(context: Context) {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(MarginItemDecoration(12))
        recyclerView.adapter = ToDoRecyclerViewAdapter(ArrayList<ToDo>())
    }

    private fun attachViews(rootView: View) {
        recyclerView = rootView.findViewById(R.id.main_todo_recycler_view)
    }
}