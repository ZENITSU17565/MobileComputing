package com.singhvikrant.mobilecomputingtasks.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.singhvikrant.mobilecomputingtasks.R
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteEntity
import com.singhvikrant.mobilecomputingtasks.ui.MainActivity


class NoteNotification: BroadcastReceiver(){

//    private val channelId = "remindMe"
//    private val notificationId = 256

    private val channelId = "remindMeNote"
    private val notificationId = 123

    override fun onReceive(context: Context, intent: Intent) {

        println("Notifications")

        val bundle = intent.getBundleExtra("bundle")


        val title = intent.getStringExtra("title").toString()
        var note = intent.getStringExtra("note").toString()
        var dateTime = intent.getStringExtra("dateTime").toString()
        println("title @ $title")
        println("note @ $note")
        if (note.isEmpty()){
            note = "No description"
        }

        val noteEntity: NoteEntity = bundle?.getSerializable("noteEntity") as NoteEntity
        println("noteEntity $noteEntity")


        val newIntent = Intent(context, MainActivity::class.java)
        newIntent.putExtra("title", title)
        newIntent.putExtra("note", note)
        newIntent.putExtra("dateTime", dateTime)
        newIntent.putExtra("noteEntity", bundle.getSerializable("noteEntity") as NoteEntity)
        val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Working @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(note)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager:NotificationManagerCompat = NotificationManagerCompat.from(context)

        notificationManager.notify(notificationId, builder.build())

    }



}