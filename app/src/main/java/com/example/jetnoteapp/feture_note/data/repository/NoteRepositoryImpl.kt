package com.example.jetnoteapp.feture_note.data.repository

import com.example.jetnoteapp.feture_note.data.data_source.NoteDao
import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }


}