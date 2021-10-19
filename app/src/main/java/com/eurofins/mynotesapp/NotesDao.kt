package com.eurofins.mynotesapp

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    // If there is data with the same id then it will be ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("select * from notesTable order by id ASC")
    fun getAllNotes(): Flow<List<Note>>

}