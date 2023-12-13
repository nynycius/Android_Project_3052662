package com.gdc.androidproject3052662

import com.gdc.androidproject3052662.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object MongoDB : MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    // configure Mongo Realm
    override fun configureTheRealm(){
        if (user != null){
            val config = SyncConfiguration.Builder(
                user,
                setOf(User::class, Build::class)
            )
                .initialSubscriptions{sub ->
                add(query = sub.query<User>(query = "owner_id == $0, user.id"))
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    // return Boolean list of Favorites builds
    override fun getFav(): Flow<RealmResults<User>> {
        return realm.query<User>(query = "favorite").asFlow().map {it.list}

    }

    // return Boolean list of Found builds
    override fun getFound(): Flow<RealmResults<User>> {
        return realm.query<User>(query = "isFound").asFlow().map {it.list}

    }

//    override suspend fun updateFav(build: Int){
//        realm.write{
//            val favorite : RealmList<Boolean> =
//                query<User>(query = "favorite")
//
//           if(favorite[build]){
//
//            }
//        }
//    }



}

