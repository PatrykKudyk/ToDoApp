package com.partos.todoapp.logic

import android.content.Context
import android.widget.Toast
import com.partos.todoapp.R

class ToastHelper {

    fun toastNoText(context: Context) {
        Toast.makeText(context, context.getText(R.string.toast_no_text), Toast.LENGTH_SHORT).show()
    }
}