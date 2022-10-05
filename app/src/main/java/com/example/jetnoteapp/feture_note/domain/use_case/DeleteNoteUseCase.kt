package com.example.jetnoteapp.feture_note.domain.use_case

import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    val repository: NoteRepository
) {

    suspend operator fun invoke(note :Note){
        repository.deleteNote(note)
    }

}