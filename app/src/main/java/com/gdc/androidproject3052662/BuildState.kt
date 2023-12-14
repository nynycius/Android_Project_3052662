package com.gdc.androidproject3052662

// create dataclass with the state of the retrieve builds
data class BuildState(
    val builds: List<Builds> = emptyList(),
    val name: String = "",
    val descrip: String = "",
    val address: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val found: Boolean = false,
    val favorite: Boolean = false,

)