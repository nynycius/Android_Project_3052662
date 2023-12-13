package com.gdc.androidproject3052662

import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

interface MongoRepository {

    fun configureTheRealm()
    fun getFav(): Flow<RealmResults<User>>
    fun getFound(): Flow<RealmResults<User>>
}