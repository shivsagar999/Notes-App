package com.eurofins.mynotesapp.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eurofins.mynotesapp.database.NotesDao

class TrashViewModelFactory(private val notesDao: NotesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrashViewModel::class.java)) {
            return TrashViewModel(notesDao) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}