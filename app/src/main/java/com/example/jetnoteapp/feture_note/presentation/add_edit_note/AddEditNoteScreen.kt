package com.example.jetnoteapp.feture_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetnoteapp.feture_note.domain.util.noteColors
import com.example.jetnoteapp.feture_note.presentation.add_edit_note.component.TransparentTextField
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel(),
    recentNoteColor: Int
) {
    val title = viewModel.titleState.value
    val content = viewModel.contentState.value

    val animatedColor = remember {
        Animatable(
            Color(if (recentNoteColor != -1) recentNoteColor else viewModel.colorState.value)
        )
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(true){
        viewModel.uiEvent.collectLatest{uiEvent ->
            when(uiEvent){
                AddEditViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
                is AddEditViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
            }

        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(animatedColor.value)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                noteColors.forEach { color ->
                    val noteColor = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(20.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (noteColor == viewModel.colorState.value) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    animatedColor.animateTo(
                                        targetValue = Color(noteColor),
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ColorChanged(noteColor))
                            }
                    )


                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = title.text,
                hint = title.hint,
                onTextChange = {viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))},
                onFocusChange = {viewModel.onEvent(AddEditNoteEvent.FocusTitleChange(it))},
                isHintVisible = title.isShowHint,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(12.dp))

            TransparentTextField(
                text = content.text,
                hint = content.hint,
                onTextChange = {viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))},
                onFocusChange = {viewModel.onEvent(AddEditNoteEvent.FocusContentChange(it))},
                isHintVisible = content.isShowHint,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )




        }


    }


}