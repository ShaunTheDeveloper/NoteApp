package com.example.jetnoteapp.feture_note.presentation.notes

import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.util.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder) : NoteEvent()
    data class DeleteNote(val note:Note) : NoteEvent()
    object RestoreNote : NoteEvent()
    object OrderToggleSelected : NoteEvent()
}
