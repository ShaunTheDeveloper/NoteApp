package com.example.jetnoteapp.feture_note.domain.use_case

import com.example.jetnoteapp.feture_note.domain.model.InvalidNoteException
import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.repository.NoteRepository
import kotlin.jvm.Throws



class AddNoteUseCase(val repository: NoteRepository) {


    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank())
            throw InvalidNoteException("Title of Note is Blank")
        if (note.content.isBlank())
            throw InvalidNoteException("Content of Note is Blank")

        repository.insertNote(note)
    }

}