package com.singhvikrant.mobilecomputingtasks.ui

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.singhvikrant.mobilecomputingtasks.R
import com.singhvikrant.mobilecomputingtasks.dateTime.DateTime
import com.singhvikrant.mobilecomputingtasks.notification.NoteNotification
import com.singhvikrant.mobilecomputingtasks.notification.SetAlarm
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteEntity
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class NoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private val channelId = "remindMe"
    private val notificationId = 256

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        createNotificationChannel(channelId, notificationId)

        val inputDescription:TextInputEditText = findViewById(R.id.input_description)
        inputDescription.gravity = Gravity.TOP
        inputDescription.setHorizontallyScrolling(false)
        inputDescription.maxLines = 17

        val dt = DateTime(this)

        val alarmManager:AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val saveNote: Button = findViewById(R.id.saveNote)
        saveNote.setOnClickListener {
            if (validateTitle()) {

                val title: TextInputLayout = findViewById(R.id.text_input_title)
                val titleInput = title.editText?.text.toString()

                val note: TextInputLayout = findViewById(R.id.text_input_description)
                val noteInput = note.editText?.text.toString()

                val dateTimeText: TextView = findViewById(R.id.date_time)
                val dateTimeTextInput = dateTimeText.text.toString()

                val dateTime = dt.getDateTime()
                println(dateTime)

                val formatter = SimpleDateFormat("dd/MM/yyyy , HH:mm", Locale.ENGLISH)

                val dateTimeToMs :Long= formatter.parse(dateTime)!!.time
                println(dateTimeToMs)

                val timeOfNoteSaved: Long = System.currentTimeMillis()
                println(timeOfNoteSaved)

                val delay: Long = 0
                println(delay)

                val noteEntity = NoteEntity(titleInput, noteInput, dateTimeTextInput)

                viewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]
                viewModel.insertNote(noteEntity)

                val intent = Intent(this, NoteNotification::class.java)

                val bundle = Bundle()
                bundle.putSerializable("noteEntity", noteEntity)

                intent.putExtra("title", titleInput)
                intent.putExtra("note", noteInput)
                intent.putExtra("dateTime", dateTimeTextInput)

                intent.putExtra("bundle",bundle)
                println("noteEntity $noteEntity")


                val pendingIntent = PendingIntent.getBroadcast(this, dateTimeToMs.toInt(), intent,PendingIntent.FLAG_CANCEL_CURRENT)

                SetAlarm(dateTimeToMs, pendingIntent, alarmManager)

                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }

    fun createNotificationChannel(channelId: String, notificationId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = channelId
            val descriptionText = "This is a reminder notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager =  getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            val notificationManager = ContextCompat.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }


    private fun validateTitle(): Boolean {
        val title:TextInputLayout = findViewById(R.id.text_input_title)
        val titleInput = title.editText?.text.toString()
        return if (titleInput.isEmpty()) {
            title.error = "Field can't be empty"
            false
        } else {
            title.error = null
            true
        }
    }
}