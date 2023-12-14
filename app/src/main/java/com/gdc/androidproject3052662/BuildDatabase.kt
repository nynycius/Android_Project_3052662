package com.gdc.androidproject3052662

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// This class represents the Room database and contains a method to get the DAO
@Database(
    entities = [Builds::class],
    version= 1
)
abstract class BuildDatabase : RoomDatabase() {

    // declare where to find the Data access object (Dao)
    abstract val dao : BuildDao

}