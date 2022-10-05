package com.example.jetnoteapp.feture_note.presentation.notes

import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.util.NoteOrder
import com.example.jetnoteapp.feture_note.domain.util.OrderType

data class NotesState(
    val notes : List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending),
    val orderToggleSelection : Boolean = false
)
