package com.eurofins.mynotesapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurofins.mynotesapp.data.Note
import com.eurofins.mynotesapp.data.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val notesDao: NotesDao) : ViewModel() {

    // editNote stores the Note fetched when user wants to change any particular note
    private var _editNote = MutableLiveData<Note>()
    val editNote: LiveData<Note> get() = _editNote

    private var _searchResults = MutableLiveData<List<Note>>()
    val searchResults: LiveData<List<Note>> get() = _searchResults



    var selectedPosition = mutableMapOf<Int, Note>()

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesDao.update(note)
        }
    }

    fun getSearchResults(searchText: String){
            viewModelScope.launch {
                _searchResults.value = notesDao.getSearchResult(searchText)

            }
        Log.d("Wagle", "$searchResults  search text $searchText")


    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesDao.insert(note)
        }
    }

    fun updateNoteToDeleted(note: Note) {
        note.isDeleted = true
        viewModelScope.launch {
            notesDao.update(note)
        }
    }

    fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    fun getNote(id: Int) {
        viewModelScope.launch {
            _editNote.value = notesDao.getNote(id = id)
        }
    }

    fun updateNote(title: String, description: String) {
        var newNote = _editNote.value
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