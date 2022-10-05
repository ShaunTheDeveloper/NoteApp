package com.example.jetnoteapp.feture_note.domain.use_case

import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.repository.NoteRepository

class GetNoteByIDUseCase(val noteRepository: NoteRepository) {


    suspend operator fun invoke(id:Int):Note?{
        return noteRepository.getNoteById(id)
    }
}