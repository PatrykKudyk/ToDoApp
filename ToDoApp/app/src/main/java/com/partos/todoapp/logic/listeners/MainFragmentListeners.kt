package com.partos.todoapp.logic.listeners

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.db.DataBaseHelper
import com.partos.todoapp.MyApp
import com.partos.todoapp.R
import com.partos.todoapp.adapters.recycler.ToDoRecyclerViewAdapter
import com.partos.todoapp.models.ToDo

class MainFragmentListeners {

    private lateinit var deleteButton: Button

    fun initListeners(rootView: View, recyclerView: RecyclerView) {
        attachViews(rootView)
        attachListeners(recyclerView, rootView.context)
    }

    private fun attachListeners(recyclerView: RecyclerView, context: Context) {
        deleteButton.setOnClickListener {
            val db = DataBaseHelper(context)
            val toDoList = db.getToDoList(MyApp.dateId)
            for (item in toDoList) {
                if (item.isDone == 1) {
                    db.deleteToDo(item.id)
                }
            }
            val newList = db.getToDoList(MyApp.dateId)
            newList.add(ToDo(-1, MyApp.dateId, "no", 0))
            recyclerView.adapter = ToDoRecyclerViewAdapter(newList)
        }
    }

    private fun attachViews(rootView: View) {
        deleteButton = rootView.findViewById(R.id.main_todo_button_delete)
    }
}