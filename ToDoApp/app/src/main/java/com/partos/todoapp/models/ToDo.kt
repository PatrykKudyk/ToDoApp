package com.partos.todoapp.models

data class ToDo(
    var id: Long,
    var dateId: Long,
    var text: String,
    var isDone: Int
)