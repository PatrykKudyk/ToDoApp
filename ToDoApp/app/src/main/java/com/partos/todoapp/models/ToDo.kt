package com.partos.todoapp.models

data class ToDo(
    var id: Long,
    var isDone: Int,
    var text: String
)