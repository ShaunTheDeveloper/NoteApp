package com.example.jetnoteapp.feture_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnoteapp.feture_note.domain.model.InvalidNoteException
import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.use_case.NoteUseCases
import com.example.jetnoteapp.feture_note.domain.util.noteColors
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    val noteUseCases: NoteUseCases,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _titleState = mutableStateOf(TextFieldState(hint = "Title...."))
    val titleState: State<TextFieldState> = _titleState

    private val _contentSate = mutableStateOf(TextFieldState(hint = "Content...."))
    val contentState: State<TextFieldState> = _contentSate

    private val _colorState = mutableStateOf(noteColors.random().toArgb())
    val colorState: State<Int> = _colorState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }

    var currentID: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            if (it != -1) {
                currentID = it
                viewModelScope.launch {
                    noteUseCases.getNoteByIDUseCase(it)?.also { note ->
                        _titleState.value =
                            titleState.value.copy(text = note.title, isShowHint = false)
                        _contentSate.value =
                            contentState.value.copy(text = note.content, isShowHint = false)
                        _colorState.value = note.color
                    }
                }

            }

        }
    }


    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredContent -> {
                _contentSate.value = contentState.value.copy(
                    text = event.content
                )
            }
            is AddEditNoteEvent.EnteredTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.title
                )
            }
            is AddEditNoteEvent.FocusContentChange -> {
                _contentSate.value = contentState.value.copy(
                    isShowHint = !event.focusState.isFocused && contentState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.FocusTitleChange -> {
                _titleState.value = titleState.value.copy(
                    isShowHint = !event.focusState.isFocused && titleState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                title = titleState.value.text,
                                content = contentState.value.text,
                                color = colorState.value,
                                timeStamp = System.currentTimeMillis(),
                                id = currentID
                            )
                        )
                        _uiEvent.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _uiEvent.emit(UiEvent.ShowSnackbar("Note don't Saved"))
                    }
                }
            }
            is AddEditNoteEvent.ColorChanged -> {
                _colorState.value = event.color
            }

        }


    }


}