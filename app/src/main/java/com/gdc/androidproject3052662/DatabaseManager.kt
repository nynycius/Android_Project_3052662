package com.gdc.androidproject3052662

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// set database
class DatabaseManager (context: Context, name :String, factory: SQLiteDatabase.CursorFactory?, version : Int) : SQLiteOpenHelper (context, name, factory, version){

    // create table on create
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createCommand)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(createCommand)
        db?.execSQL(dropCommand)
    }

    private val createCommand = "CREATE TABLE Builds(id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, found BOOLEAN)"
    private val dropCommand =  "DROP TABLE Builds"


}