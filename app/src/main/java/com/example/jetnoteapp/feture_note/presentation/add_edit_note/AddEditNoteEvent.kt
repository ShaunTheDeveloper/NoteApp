package com.example.jetnoteapp.feture_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle(val title:String)  :AddEditNoteEvent()
    data class EnteredContent(val content:String)  :AddEditNoteEvent()
    data class FocusTitleChange(val focusState: FocusState)  :AddEditNoteEvent()
    data class FocusContentChange(val focusState: FocusState)  :AddEditNoteEvent()
    data class ColorChanged(val color : Int) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()

}