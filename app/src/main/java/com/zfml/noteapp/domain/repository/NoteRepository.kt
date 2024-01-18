package com.zfml.noteapp.domain.repository

import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.model.Response
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias Notes = Map<LocalDate,List<Note>>
typealias NotesResponse = Response<Notes>
interface NoteRepository {
    suspend fun  getAllNote(): Flow<NotesResponse>

    suspend fun getNote(noteId: String): Response<Note>
    suspend fun addNote(note: Note): Response<Boolean>

    suspend fun updateNote(note: Note): Response<Boolean>

    suspend fun deleteNote(noteId: String): Response<Boolean>


}