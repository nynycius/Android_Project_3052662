package com.gdc.androidproject3052662

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity declaration works as a Schema
@Entity(tableName = "Build")
data class Builds(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val descrip: String,
    val address: String,
    // store coordinates of the location
    val lat: Double,
    val long: Double,
    val found: Boolean,
    val favorite: Boolean,
)