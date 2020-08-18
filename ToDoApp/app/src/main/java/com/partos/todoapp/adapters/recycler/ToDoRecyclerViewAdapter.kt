package com.partos.todoapp.adapters.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.partos.todoapp.R
import com.partos.todoapp.models.ToDo

class ToDoRecyclerViewAdapter(val todoList: ArrayList<ToDo>) :
    RecyclerView.Adapter<ToDoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.row_to_do, parent, false)
        return ToDoViewHolder(cell)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {

    }

}

class ToDoViewHolder(view: View) : RecyclerView.ViewHolder(view)