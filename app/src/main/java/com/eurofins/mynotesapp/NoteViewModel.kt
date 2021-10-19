package com.eurofins.mynotesapp

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(val notesDao: NotesDao) : ViewModel() {
    private var _note = MutableLiveData<Note>()
    val note : LiveData<Note> get() = _note
    var k: Int = 18

    fun deleteNote(note: Note) {
        notesDao.delete(note)
    }

    fun update(note: Note){
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