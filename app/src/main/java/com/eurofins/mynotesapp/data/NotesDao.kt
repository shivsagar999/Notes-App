package com.eurofins.mynotesapp.data

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

    @Query("select * from notesTable WHERE isDeleted = 0 order by id ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notesTable WHERE id =:id")
    suspend fun getNote(id: Int): Note


    @Query("SELECT * from notesTable WHERE isDeleted = 1 order by id ASC")
    fun getAllTrashNotes(): Flow<List<Note>>

}



//@Module
//@InstallIn(ActivityComponent::class)
//abstract class NotesModule {
//
//    @Provides
//    fun bindNotesDao(
//    ): NotesDao {
//        return NoteDatabase.getDatabase(NoteApplication)
//    }
//}