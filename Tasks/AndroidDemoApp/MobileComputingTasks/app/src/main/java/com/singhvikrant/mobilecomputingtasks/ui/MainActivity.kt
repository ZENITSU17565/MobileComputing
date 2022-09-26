package com.singhvikrant.mobilecomputingtasks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.singhvikrant.mobilecomputingtasks.R
import com.singhvikrant.mobilecomputingtasks.adapter.INotesAdapter
import com.singhvikrant.mobilecomputingtasks.adapter.NotesAdapter
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteEntity
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteViewModel

class MainActivity : AppCompatActivity(), INotesAdapter{
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notesRecyclerView: RecyclerView = findViewById(R.id.notesRecyclerView)
        notesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


//        val adapter = NotesAdapter(this, this)
        val adapter = NotesAdapter(this, this)
        notesRecyclerView.adapter = adapter

        viewModel  = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]

        viewModel.notes.observe(this, Observer { list-> list?.let{
            adapter.updateList(it)
        }
        })


        val addNote: FloatingActionButton = findViewById(R.id.addNote)

        addNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onItemClick(note: NoteEntity) {
        val intent = Intent(this, NoteDescriptionActivity::class.java)
        intent.putExtra("title", note.title)
        intent.putExtra("note", note.note)
        intent.putExtra("dateTime", note.dateTime)
        intent.putExtra("noteEntity", note)
        startActivity(intent)
    }

}