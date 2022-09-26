package com.singhvikrant.mobilecomputingtasks.roomDatabase

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {
    val notes: LiveData<List<NoteEntity>> = noteDao.getAllNote()

    suspend fun insert(note:NoteEntity){
        noteDao.insert(note)
    }

    suspend fun delete(note: NoteEntity){
        noteDao.delete(note)
    }
}