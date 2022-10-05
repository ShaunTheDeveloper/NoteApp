package com.example.jetnoteapp.feture_note.data.data_source

import androidx.room.*
import com.example.jetnoteapp.feture_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {


    @Query("select * from note_tbl")
    fun getNotes() : Flow<List<Note>>


    @Query("select * from note_tbl where id=:id")
    suspend fun getNoteById(id:Int) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)





}