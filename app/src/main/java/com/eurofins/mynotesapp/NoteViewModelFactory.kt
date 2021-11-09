package com.eurofins.mynotesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eurofins.mynotesapp.database.NotesDao

class NoteViewModelFactory(
    private val notesDao: NotesDao
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("unchecked_cast")
            return  NoteViewModel(notesDao) as T
        }else{
            throw IllegalArgumentException("Viewmodel not found")
        }
    }
}