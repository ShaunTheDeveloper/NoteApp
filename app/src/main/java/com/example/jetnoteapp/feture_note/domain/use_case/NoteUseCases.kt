package com.example.jetnoteapp.feture_note.domain.use_case

class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteByIDUseCase: GetNoteByIDUseCase
)