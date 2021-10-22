package com.eurofins.mynotesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.database.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(val notesDao: NotesDao) : ViewModel() {
    private var _note = MutableLiveData<Note>()
    val note : LiveData<Note> get() = _note

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesDao.delete(note)
        }
    }

    private fun update(note: Note){
        viewModelScope.launch {
            notesDao.update(note)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesDao.insert(note)
        }
    }

    fun getAllNotes(): Flow<List<Note>>{
        return notesDao.getAllNotes()
    }

    fun getNote(id: Int) {
        viewModelScope.launch {
            _note.value =  notesDao.getNote(id = id)
        }
    }

    fun updateNote(title: String, description: String){

            var newNote = _note.value
            newNote?.noteTitle = title
            newNote?.noteDescription = description
            update(newNote!!)

    }

}