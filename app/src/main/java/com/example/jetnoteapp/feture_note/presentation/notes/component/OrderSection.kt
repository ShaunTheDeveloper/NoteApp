package com.example.jetnoteapp.feture_note.presentation.notes.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetnoteapp.feture_note.domain.util.NoteOrder
import com.example.jetnoteapp.feture_note.domain.util.OrderType


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending),
    onNoteOrderChange: (NoteOrder) ->Unit = {}
) {

    Column(
        modifier =modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DefaultRadioButton(text = "Date", selected = noteOrder is NoteOrder.ByDate) {
                onNoteOrderChange(NoteOrder.ByDate(noteOrder.orderType))
            }
            Spacer(modifier = Modifier.width(10.dp))
            DefaultRadioButton(text = "Title", selected = noteOrder is NoteOrder.ByTitle) {
                onNoteOrderChange(NoteOrder.ByTitle(noteOrder.orderType))
            }
            Spacer(modifier = Modifier.width(10.dp))
            DefaultRadioButton(text = "Color", selected = noteOrder is NoteOrder.ByColor) {
                onNoteOrderChange(NoteOrder.ByColor(noteOrder.orderType))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {


            DefaultRadioButton(text = "Ascending", selected = noteOrder.orderType is OrderType.Ascending) {
                onNoteOrderChange(noteOrder.copy(orderType = OrderType.Ascending))
            }

            Spacer(modifier = Modifier.width(10.dp))

            DefaultRadioButton(text = "Descending", selected = noteOrder.orderType is OrderType.Descending) {
                onNoteOrderChange(noteOrder.copy(orderType = OrderType.Descending))
            }

        }

    }


}

