package com.partos.flashback.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.partos.todoapp.models.ToDo

object TableInfo : BaseColumns {
    const val DATABASE_NAME = "ToDoDB"
    const val TABLE_NAME_TO_DO = "Todo"
    const val TABLE_COLUMN_TO_DO_TEXT = "text"
    const val TABLE_COLUMN_TO_DO_IS_DONE = "isDone"
}

object BasicCommand {
    const val SQL_CREATE_TABLE_TO_DO =
        "CREATE TABLE ${TableInfo.TABLE_NAME_TO_DO} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_TO_DO_TEXT} TEXT NOT NULL," +
                "${TableInfo.TABLE_COLUMN_TO_DO_IS_DONE} INTEGER NOT NULL)"

    const val SQL_DELETE_TABLE_TO_DO = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME_TO_DO}"
}

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE_TO_DO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(BasicCommand.SQL_DELETE_TABLE_TO_DO)
        onCreate(db)
    }


    fun getToDoList(): ArrayList<ToDo> {
        var toDoList = ArrayList<ToDo>()
        val db = readableDatabase
        val selectQuery = "Select * from ${TableInfo.TABLE_NAME_TO_DO}"
        val result = db.rawQuery(selectQuery, null)
        if (result.moveToFirst()) {
            do {
                var myToDo = ToDo(
                    result.getInt(result.getColumnIndex(BaseColumns._ID)).toLong(),
                    result.getString(result.getColumnIndex(TableInfo.TABLE_COLUMN_TO_DO_TEXT)),
                    result.getInt(result.getColumnIndex(TableInfo.TABLE_COLUMN_TO_DO_IS_DONE))
                )
                toDoList.add(myToDo)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return toDoList
    }

    fun addToDo(text: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_TO_DO_TEXT, text)
        values.put(TableInfo.TABLE_COLUMN_TO_DO_IS_DONE, 0)
        db.insert(TableInfo.TABLE_NAME_TO_DO, null, values)
        db.close()
    }

    fun updateToDo(toDo: ToDo) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TableInfo.TABLE_COLUMN_TO_DO_TEXT, toDo.text)
        values.put(TableInfo.TABLE_COLUMN_TO_DO_IS_DONE, toDo.isDone)
        db.update(
            TableInfo.TABLE_NAME_TO_DO, values, BaseColumns._ID + "=?",
            arrayOf(toDo.id.toString())
        )
        db.close()
    }

    fun deleteToDo(toDoId: Long): Boolean {
        val db = this.writableDatabase
        val success =
            db.delete(
                TableInfo.TABLE_NAME_TO_DO,
                BaseColumns._ID + "=?",
                arrayOf(toDoId.toString())
            )
                .toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
}