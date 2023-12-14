package com.gdc.androidproject3052662

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface BuildDao {

    // upsert used to insert or update in case of conflict, suspend fun runs in coroutine
    @Upsert
    fun upsertBuild(entity: Builds)

    // return all Entries
    @Query("SELECT * FROM Build")
    fun getBuilds(): List<Builds>

    // check status of build, return Boolean
    @Query("SELECT found FROM Build WHERE id == :id")
    fun isFound(id : Int) : Boolean


}