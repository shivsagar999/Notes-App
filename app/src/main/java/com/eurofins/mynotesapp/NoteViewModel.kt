package com.eurofins.mynotesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(val notesDao: NotesDao, application: Application) : AndroidViewModel(application) {
    private var _note = MutableLiveData<Note>()
    val note : LiveData<Note> get() = _note


    fun deleteNote(note: Note) {
        notesDao.delete(note)
    }

    fun updateNote(note: Note){
        notesDao.update(note)
    }

    fun addNote(note: Note) {
        notesDao.insert(note)
    }

    fun getAllNotes(): Flow<List<Note>>{
        return notesDao.getAllNotes()
    }

    fun getNote(id: Int) {

        viewModelScope.launch {
            _note.value =  notesDao.getNote(id = id)
        }

    }


    /*
    private val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }*/
}