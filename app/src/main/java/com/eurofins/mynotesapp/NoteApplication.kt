package com.eurofins.mynotesapp

import android.app.Application

class NoteApplication : Application(){

    val database : NoteDatabase by lazy{ NoteDatabase.getDatabase(this)}
}