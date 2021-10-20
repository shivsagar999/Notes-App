package com.eurofins.mynotesapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    // If there is data with the same id then it will be ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("select * from notesTable order by id ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notesTable WHERE id =:id")
    suspend fun getNote(id: Int): Note

}