package com.gdc.androidproject3052662

import android.app.Application
import java.util.concurrent.Flow

class BuildRepository(app : Application) {

      lateinit var dao : BuildDao
      lateinit var allBuilds : List<Builds>

//      private val database = BuildDatabase.InitDatabase
}