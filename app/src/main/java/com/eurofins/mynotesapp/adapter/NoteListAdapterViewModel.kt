package com.eurofins.mynotesapp.adapter


import androidx.lifecycle.ViewModel
import com.eurofins.mynotesapp.database.Note

class NoteListAdapterViewModel: ViewModel() {
    var selected:Boolean = false

     var selectedNotes: ArrayList<Note> = ArrayList()


    fun incrementSelectedNote(note: Note){
        selectedNotes.add(note)
    }
    fun decrementSelectedNote(note: Note){
        selectedNotes.remove(note)
    }

    fun noteSelected(){
        selected = true
    }

    fun noteSelectCancelled(){
        selected = false
    }


}