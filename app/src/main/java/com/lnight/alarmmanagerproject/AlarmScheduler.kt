package com.lnight.alarmmanagerproject

interface AlarmScheduler {

    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)

}