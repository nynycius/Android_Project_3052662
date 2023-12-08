// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false

//  Extra plugins for maps and location
    id ("com.android.library") version "8.0.2" apply false
    id ("com.google.dagger.hilt.android") version "2.44" apply false

    // plugins for Mangodb Realm
    id ("io.realm.kotlin") version "1.11.0" apply false


}
