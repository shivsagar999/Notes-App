package com.eurofins.mynotesapp

import android.app.Application
import com.eurofins.mynotesapp.database.NoteDatabase

class NoteApplication : Application(){
    val database : NoteDatabase by lazy{ NoteDatabase.getDatabase(this)}
}