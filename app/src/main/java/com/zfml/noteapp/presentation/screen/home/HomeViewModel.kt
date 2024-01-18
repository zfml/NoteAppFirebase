package com.zfml.noteapp.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.domain.repository.NoteRepository
import com.zfml.noteapp.domain.repository.NotesResponse
import com.zfml.noteapp.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    val userName = Firebase.auth.currentUser?.displayName
    val image = Firebase.auth.currentUser?.photoUrl

    var notesRespones by mutableStateOf<NotesResponse>(Response.Loading)
        private set

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getAllNote().collect { responses ->
                notesRespones = responses
            }
        }
    }

    fun signOut(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            Firebase.auth.signOut()
            onSuccess()
        }
    }
}