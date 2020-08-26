package com.partos.todoapp.calendar

import android.content.Context
import android.icu.util.Calendar
import com.partos.flashback.db.DataBaseHelper
import com.partos.todoapp.models.Date
import kotlin.collections.ArrayList

class CalendarHelper(val context: Context) {
    fun getDatesList(): ArrayList<ArrayList<Date>> {
        val db = DataBaseHelper(context)
        var monthsList = ArrayList<ArrayList<Date>>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        for (year in currentYear..(currentYear + 2)) {
            if (year == currentYear) {
                for (month in Calendar.getInstance().get(Calendar.MONTH)..12) {
                    monthsList.add(db.getMonth(month, year))
                }
            } else {
                for (month in 1..12) {
                    monthsList.add(db.getMonth(month, year))
                }
            }
        }

        return monthsList
    }

}