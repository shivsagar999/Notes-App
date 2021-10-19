package com.eurofins.mynotesapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(
    val notesDao: NotesDao,
    private val application: Application
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("unchecked_cast")
            return  NoteViewModel(notesDao, application) as T
        }else{
            throw IllegalArgumentException("Viewmodel not found")
        }
    }
}