package com.eurofins.mynotesapp.database

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
    suspend fun delete(note: Note)

    @Query("select * from notesTable order by id ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notesTable WHERE id =:id")
    suspend fun getNote(id: Int): Note

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoTrashTable(trashNote: TrashNote)

    @Update
    suspend fun updateTrashTable(trashNote: TrashNote)

    @Delete
    suspend fun deleteFromTrashTable(trashNote: TrashNote)

    @Query("SELECT * from trashTable order by id ASC")
    fun getAllTrashNotes(): Flow<List<TrashNote>>

}