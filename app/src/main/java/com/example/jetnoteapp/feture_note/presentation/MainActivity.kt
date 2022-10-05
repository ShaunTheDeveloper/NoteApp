package com.example.jetnoteapp.feture_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetnoteapp.feture_note.domain.util.NoteOrder
import com.example.jetnoteapp.feture_note.domain.util.OrderType
import com.example.jetnoteapp.feture_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.jetnoteapp.feture_note.presentation.notes.NoteScreen

import com.example.jetnoteapp.feture_note.presentation.util.Screen

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.NoteScreen.route ){
                    composable(Screen.NoteScreen.route){
                        NoteScreen(navController = navController)
                    }
                    composable(Screen.AddEditNoteScreen.route +
                    "?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ){
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "noteColor"
                        ){
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, recentNoteColor = color)
                    }




                }

            }

        }
    }
}

