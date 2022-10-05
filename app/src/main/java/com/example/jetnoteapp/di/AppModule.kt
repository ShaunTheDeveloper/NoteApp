package com.example.jetnoteapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetnoteapp.feture_note.data.data_source.NoteDao
import com.example.jetnoteapp.feture_note.data.data_source.NoteDatabase
import com.example.jetnoteapp.feture_note.data.repository.NoteRepositoryImpl
import com.example.jetnoteapp.feture_note.domain.model.Note
import com.example.jetnoteapp.feture_note.domain.repository.NoteRepository
import com.example.jetnoteapp.feture_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun providesDataBase(@ApplicationContext context : Context): NoteDatabase{
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "Note_DB"
        )
            .build()

    }

    @Singleton
    @Provides
    fun providesRepository(database: NoteDatabase) : NoteRepository{
        return NoteRepositoryImpl(noteDao = database.noteDao)
    }


    @Singleton
    @Provides
    fun providesUseCase(noteRepository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository),
            getNoteByIDUseCase = GetNoteByIDUseCase(noteRepository)
        )
    }



}