package com.eurofins.mynotesapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashNotesDao {

    @Insert
    fun insert(trashNote: TrashNote)

    @Delete
    fun delete(trashNote: TrashNote)



    @Insert
    fun insertIntoNoteTable(note: Note)

}