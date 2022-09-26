package com.singhvikrant.mobilecomputingtasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singhvikrant.mobilecomputingtasks.R
import com.singhvikrant.mobilecomputingtasks.roomDatabase.NoteEntity

class NotesAdapter(private val context: Context, private val listener: INotesAdapter):RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesArrayList = ArrayList<NoteEntity>()

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView? = itemView.findViewById(R.id.title)
        val notes:TextView? = itemView.findViewById(R.id.notes)
        val clickable:TextView = itemView.findViewById(R.id.clickable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val viewHolder = NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_notes_recycler, parent, false))
        viewHolder.clickable.setOnClickListener{
            listener.onItemClick(notesArrayList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notesArrayList[position]
        holder.title?.text = currentNote.title
        holder.notes?.text = currentNote.note
    }

    override fun getItemCount(): Int {
        return notesArrayList.size
    }

    fun updateList(newList:List<NoteEntity>){
        notesArrayList.clear()
        notesArrayList.addAll(newList)

        notifyDataSetChanged()
    }

}



interface INotesAdapter {
    fun onItemClick(note: NoteEntity)
}

