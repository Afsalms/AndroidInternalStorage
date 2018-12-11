package com.example.qbuser.androidinternalstorage.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.qbuser.androidinternalstorage.models.InputEntries

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DatabaseHandler.DB_NAME,
    null, DatabaseHandler.DB_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                TEXT + " TEXT,"+
                );"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }
    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "TestDb"
        private val TABLE_NAME = "inputEntries"
        private val ID = "id"
        private val TEXT = "text"
    }
    fun addEntry(entries:InputEntries):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TEXT, entries.text)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun getAllEntries(): ArrayList<InputEntries> {
        var entriesList = ArrayList<InputEntries>()
        val db = this.writableDatabase
        val queryStr = "SELECT *  from $DB_NAME"
        val cursor = db.rawQuery(queryStr, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                val entry = InputEntries()
                entry.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                entry.text = cursor.getString(cursor.getColumnIndex(TEXT))
                entriesList.add(entry)

            }

            }
        return entriesList
        }
}