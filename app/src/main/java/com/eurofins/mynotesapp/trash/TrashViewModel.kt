package com.eurofins.mynotesapp.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.database.NotesDao
import com.eurofins.mynotesapp.database.TrashNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TrashViewModel(val notesDao: NotesDao): ViewModel() {

    fun addNoteToNotesTable(trashNote: TrashNote)
    {   var note = Note(trashNote.noteTitle, trashNote.noteDescription,
                        trashNote.timeStamp)

        viewModelScope.launch {
            notesDao.insert(note)
        }
    }

    fun getAllTrashNotes(): Flow<List<TrashNote>>{
        return notesDao.getAllTrashNotes()
    }

}