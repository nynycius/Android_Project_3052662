package com.gdc.androidproject3052662

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// allow to access DAO states
class BuildViewModel(private val dao : BuildDao) : ViewModel() {

    private val _state = MutableStateFlow(BuildState())

    fun onEvent(event: BuildEvent){
        when(event){
            // return if build was found
            is BuildEvent.BuildFound -> {
                dao.isFound(event.buildId)
            }

            // used to add builds
            is BuildEvent.AddBuild -> {
                val build = Builds(
                    name = _state.value.name,
                    descrip = _state.value.descrip,
                    address = _state.value.address,
                    lat = _state.value.lat,
                    long  = _state.value.long,
                    found = _state.value.found,
                    favorite = _state.value.favorite
                )

                viewModelScope.launch {
                    dao.upsertBuild(build)
                }

                _state.update {
                    it.copy(
                        name = "",
                        descrip = "",
                        address = "",
                        lat = 0.00,
                        long  = 0.00,
                        found = false,
                        favorite = false
                    )
                }
            }

        }
    }

}