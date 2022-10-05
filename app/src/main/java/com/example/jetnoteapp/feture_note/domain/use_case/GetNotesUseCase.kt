package com.example.jetnoteapp.feture_note.domain.use_case

import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.repository.NoteRepository
import com.example.jetnoteapp.feture_note.domain.util.NoteOrder
import com.example.jetnoteapp.feture_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending)
    ): Flow<List<Note>>{

        return repository.getNotes().map {
            when(noteOrder.orderType){
                OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.ByColor -> it.sortedBy { it.color }
                        is NoteOrder.ByDate ->  it.sortedBy { it.timeStamp }
                        is NoteOrder.ByTitle -> it.sortedBy { it.title }
                    }
                }
                OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.ByColor -> it.sortedByDescending { it.color }
                        is NoteOrder.ByDate ->  it.sortedByDescending { it.timeStamp }
                        is NoteOrder.ByTitle -> it.sortedByDescending { it.title }
                    }

                }
            }



        }

    }

}