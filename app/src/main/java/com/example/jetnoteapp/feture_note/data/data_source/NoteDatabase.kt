package com.example.jetnoteapp.feture_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetnoteapp.feture_note.domain.model.Note


@Database(
    version = 1,
    exportSchema = false,
    entities = [Note::class]
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao : NoteDao
}