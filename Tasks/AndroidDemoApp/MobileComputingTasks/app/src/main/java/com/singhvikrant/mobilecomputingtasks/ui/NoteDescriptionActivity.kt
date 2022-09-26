package com.singhvikrant.mobilecomputingtasks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.singhvikrant.mobilecomputingtasks.R
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteEntity
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteViewModel

class NoteDescriptionActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_description)

        val title = intent.getStringExtra("title").toString()
        var note = intent.getStringExtra("note").toString()
        var dateTime = intent.getStringExtra("dateTime").toString()
        val noteEntity: NoteEntity = intent.getSerializableExtra("noteEntity") as NoteEntity
        if (note.isEmpty()){
            note = "No description"
        }

        val deleteNote:ImageView = findViewById(R.id.deleteNote)

        val titleText:TextView = findViewById(R.id.title)
        titleText.text = title

        val noteText:TextView = findViewById(R.id.note)
        noteText.text = note

        val dateTimeText:TextView = findViewById(R.id.dateTime)
        dateTimeText.text = dateTime

        deleteNote.setOnClickListener {
            viewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]
            viewModel.deleteNote(noteEntity)
            finish()
        }


    }

}