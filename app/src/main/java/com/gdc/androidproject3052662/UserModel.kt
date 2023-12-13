package com.gdc.androidproject3052662

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey
    var _id : ObjectId = ObjectId.invoke()
    var owner_id: String = ""
    var timestamp: RealmInstant = RealmInstant.now()
    var isFound: RealmList<Boolean> = realmListOf<Boolean>()
    var favorite: RealmList<Boolean> = realmListOf<Boolean>()
}

class Build : RealmObject{
    @PrimaryKey
    var _id : ObjectId = ObjectId.invoke()
    var buildNumb : Int = 0
    var descr : String = ""
    var img : String = ""

}