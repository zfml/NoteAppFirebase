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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val userName = Firebase.auth.currentUser!!.displayName
    private val profileImage = Firebase.auth.currentUser!!.photoUrl


    private val _notesUiState = MutableStateFlow(NotesUiState())
    val notesUiState: StateFlow<NotesUiState> = _notesUiState

    init {
        getAllNotes()

    }
    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getAllNote().collect {responese ->
                when(responese) {
                    is Response.Error -> {
                        _notesUiState.update {currentState ->
                              currentState.copy(
                                 error = responese.error.toString()
                             )
                        }
                    }
                    Response.Loading -> TODO()
                    is Response.Success -> {
                        _notesUiState.update {currentState ->
                           currentState.copy(
                               notes = responese.data
                           )
                        }
                    }
                }

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


    fun getUserName() = userName!!
    fun getUserProfileImage() =  profileImage!!

}






data class NotesUiState(
    val notes: Map<LocalDate,List<Note>> = emptyMap(),
    val error: String = ""
)