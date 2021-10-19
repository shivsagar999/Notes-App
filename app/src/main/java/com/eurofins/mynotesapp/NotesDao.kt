package com.eurofins.mynotesapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    // If there is data with the same id then it will be ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

}