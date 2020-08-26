package com.partos.todoapp.models

data class Date(
    var id: Long,
    var day: Int,
    var month: Int,
    var year: Int,
    var dayOfWeek: Int
)