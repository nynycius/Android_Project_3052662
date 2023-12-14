package com.gdc.androidproject3052662

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeDatabase {


    // initialize database and populate if it is empty
    suspend fun InitDatabase(context: Context) {
        val database = Room.databaseBuilder(
            context.applicationContext,
            BuildDatabase::class.java,
            "builds-database"
        ).build()

        val dao = database.dao

        val count = withContext(Dispatchers.IO) {
            // Get the count of entities in the database
            dao.getBuilds().size
        }

        if (count == 0) {
            // If the count is 0, populate the database
            PopulateDatabase(context)
        }

        database.close()
    }




    private suspend fun PopulateDatabase(context: Context) {
        val database = Room.databaseBuilder(
            context.applicationContext,
            BuildDatabase::class.java,
            "build_database.db"
        ).build()

        withContext(Dispatchers.IO) {
            // pass dao (declared on abstract val BuildDatabase) as a parameter
            val dao = database.dao

            // Insert initial data
            dao.upsertBuild(Builds(name = "GriffithCollege", address = "south Circular",
                descrip = "College",
                found = false,
                favorite = false,
                lat = 53.33194773410968,
                long = -6.278208659022728
                ))
            }

        database.close()
    }

}