package com.example.jetnoteapp.feture_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.use_case.NoteUseCases
import com.example.jetnoteapp.feture_note.domain.util.NoteOrder
import com.example.jetnoteapp.feture_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(val noteUseCases: NoteUseCases) : ViewModel() {
    private val _noteState = MutableStateFlow(NotesState())
    val noteState: StateFlow<NotesState> = _noteState.asStateFlow()

    private var recentlyDeletedNote: Note? = null

    private var currentJob : Job? = null

    init {
        getNotes(NoteOrder.ByDate(OrderType.Descending))
    }


    fun onEvent(noteEvent: NoteEvent) {

        when (noteEvent) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(note = noteEvent.note)
                    recentlyDeletedNote = noteEvent.note
                }
            }
            is NoteEvent.Order -> {
                if (noteEvent.noteOrder::class == noteState.value.noteOrder::class)
                    if (noteEvent.noteOrder.orderType == noteState.value.noteOrder.orderType)
                        return

                getNotes(noteEvent.noteOrder)


            }
            NoteEvent.OrderToggleSelected -> {
                _noteState.value =
                    noteState.value.copy(orderToggleSelection = !noteState.value.orderToggleSelection)
            }
            NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
        }


    }

    fun getNotes(noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending)) {
        currentJob?.cancel()
        currentJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach {
                _noteState.value = noteState.value.copy(
                    notes = it,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

}