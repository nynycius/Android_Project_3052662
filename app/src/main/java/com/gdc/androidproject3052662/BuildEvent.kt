package com.gdc.androidproject3052662

sealed interface BuildEvent {
    data class  BuildFound(val buildId: Int): BuildEvent
    // add entry to be used when start database
    data class AddBuild(
        val name: String,
        val descrip: String,
        val address: String,
        val lat: Double,
        val long: Double,
        val found: Boolean,
        val favorite: Boolean,
    ) : BuildEvent
}