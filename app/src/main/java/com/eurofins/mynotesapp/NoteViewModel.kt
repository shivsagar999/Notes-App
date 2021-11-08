package com.eurofins.mynotesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.database.NotesDao
import com.eurofins.mynotesapp.database.TrashNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(val notesDao: NotesDao) : ViewModel() {
    private var _note = MutableLiveData<Note>()
    val note: LiveData<Note> get() = _note

    var selectedPosition = mutableMapOf<Int, Note>()

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesDao.delete(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesDao.update(note)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesDao.insert(note)
        }
    }

    fun insertTrashNote(note: Note) {
        val trashNote = TrashNote(note.noteTitle, note.noteDescription, note.timeStamp)
        viewModelScope.launch { notesDao.insertIntoTrashTable(trashNote) }

    }

    fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    fun getNote(id: Int) {
        viewModelScope.launch {
            _note.value = notesDao.getNote(id = id)
        }
    }

    fun updateNote(title: String, description: String) {
        var newNote = _note.value
        newNote?.noteTitle = title
        newNote?.noteDescription = description
        updateNote(newNote!!)
    }


    fun addToDelete(note: Note, position: Int) {
        selectedPosition[position] = note

    }

    fun removeFromDelete(note: Note, position: Int) {
        selectedPosition.remove(position)
    }

}