package com.example.myscheduler

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Schedule:RealmObject() {
    @PrimaryKey
    var id:Long = 0 //予定を見分けるためのID
    var date: Date = Date() //予定の日付
    var title: String = "" //予定のタイトル
    var detail: String = "" //予定の詳細
}