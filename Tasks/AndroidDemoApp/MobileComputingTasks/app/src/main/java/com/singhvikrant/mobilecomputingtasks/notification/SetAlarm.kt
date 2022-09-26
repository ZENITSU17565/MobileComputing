package com.singhvikrant.mobilecomputingtasks.notification

import android.app.AlarmManager
import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity

class SetAlarm(time: Long, pendingIntent: PendingIntent, alarmManager: AlarmManager): AppCompatActivity() {
    init {
        alarmManager.set(AlarmManager.RTC_WAKEUP, time + 0, pendingIntent)
    }
}