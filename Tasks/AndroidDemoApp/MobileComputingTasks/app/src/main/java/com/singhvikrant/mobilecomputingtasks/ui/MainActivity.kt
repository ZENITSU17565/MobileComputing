package com.singhvikrant.mobilecomputingtasks.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!","working")
        return when (item.itemId) {
            R.id.action_about -> {
                startAbout()
                true
            }
            R.id.action_contact -> {
                startContact()
                true
            }
            R.id.action_feedback -> {
                startFeedback()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun startAbout(){
        Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!","start")
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)
    }
    fun startContact(){
        Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!","contact")
        val intent = Intent(this, ContactUsActivity::class.java)
        startActivity(intent)
    }
    fun startFeedback(){
        Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!","feedback")

        val url = "https://docs.google.com/forms/d/e/1FAIpQLSdKh6W-CfBkaJ-DPKpgoUzN_Dqu5_iDkwi0CMK7jsbRaWNdYg/viewform"


        // initializing object for custom chrome tabs.
        val customIntent: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

        // below line is setting toolbar color
        // for our custom chrome tab.

        // below line is setting toolbar color
        // for our custom chrome tab.
        customIntent.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.purple_200))

        // we are calling below method after
        // setting our toolbar color.

        // we are calling below method after
        // setting our toolbar color.
        openCustomTab(this@MainActivity, customIntent.build(), Uri.parse(url))
    }

    fun openCustomTab(activity: Activity, customTabsIntent: CustomTabsIntent, uri: Uri?) {
        // package name is the default package
        // for our custom chrome tab
        val packageName = "com.android.chrome"
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName)

            // in that custom tab intent we are passing
            // our url which we have to browse.
            if (uri != null) {
                customTabsIntent.launchUrl(activity, uri)
            }
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

}