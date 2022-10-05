package com.example.jetnoteapp.feture_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetnoteapp.feture_note.domain.util.noteColors
import com.example.jetnoteapp.feture_note.presentation.notes.NoteEvent
import com.example.jetnoteapp.feture_note.presentation.notes.NotesViewModel
import com.example.jetnoteapp.feture_note.presentation.notes.component.*
import com.example.jetnoteapp.feture_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val state = notesViewModel.noteState.collectAsState().value

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }


    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Your Notes", style = MaterialTheme.typography.h4)
                Icon(imageVector = Icons.Default.Sort, contentDescription = "icon",
                    modifier = Modifier.clickable {
                        notesViewModel.onEvent(NoteEvent.OrderToggleSelected)
                    })
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = state.orderToggleSelection,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    noteOrder = state.noteOrder,
                ) {
                    notesViewModel.onEvent(NoteEvent.Order(it))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn() {

                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            }
                    ) {
                        notesViewModel.onEvent(NoteEvent.DeleteNote(note))
                        scope.launch {
                            var result = scaffoldState.snackbarHostState.showSnackbar("Note deleted",
                            actionLabel = "Undo")
                            if (result == SnackbarResult.ActionPerformed){
                                notesViewModel.onEvent(NoteEvent.RestoreNote)
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
        }

    }





}