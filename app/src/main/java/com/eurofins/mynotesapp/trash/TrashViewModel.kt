package com.eurofins.mynotesapp.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurofins.mynotesapp.data.Note
import com.eurofins.mynotesapp.data.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class TrashViewModel (private val notesDao: NotesDao) : ViewModel() {

    var selectedPosition = mutableMapOf<Int, Note>()

    fun restoreNote(note: Note) {
        note.isDeleted = false
        viewModelScope.launch {
            notesDao.update(note)
        }
    }

    fun deleteTrashNote(note: Note) {
        viewModelScope.launch {
            notesDao.delete(note)
        }
    }

    fun getAllTrashNotes(): Flow<List<Note>> {
        return notesDao.getAllTrashNotes()
    }
}