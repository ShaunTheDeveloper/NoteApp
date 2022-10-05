package com.example.jetnoteapp.feture_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_tbl")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title : String,
    val content : String,
    val timeStamp : Long,
    val color : Int

)


class InvalidNoteException(message:String) : Exception(message)