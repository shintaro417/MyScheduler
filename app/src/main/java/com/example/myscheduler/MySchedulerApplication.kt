package com.example.myscheduler

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MySchedulerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this) //realmの初期化
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true).build() //UIスレッドでのデータベース書込みを許可する
        Realm.setDefaultConfiguration(config)
    }
}