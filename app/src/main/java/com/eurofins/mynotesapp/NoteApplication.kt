package com.eurofins.mynotesapp

import android.app.Application
import com.eurofins.mynotesapp.data.NoteDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication : Application() {
    val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
}