package com.zfml.noteapp.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.domain.repository.NoteRepository
import com.zfml.noteapp.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {



   var userName by  mutableStateOf("")
       private set

    val profileImage by mutableStateOf("")


    private val _notesUiState = MutableStateFlow(NotesUiState())
    val notesUiState: StateFlow<NotesUiState> = _notesUiState

    init {
        getAllNotes()
        getUserData()

    }

    private fun getUserData() {
        viewModelScope.launch{
            val user = noteRepository.getCurrentUser()
            userName = user?.name?: ""
        }
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getAllNote().collect {responese ->
                when(responese) {
                    is Response.Error -> {
                        _notesUiState.update {currentState ->
                              currentState.copy(
                                 error = responese.error.toString(),
                                  loading = false
                             )
                        }
                    }
                    Response.Loading -> {
                        _notesUiState.update {
                            it.copy(
                                loading = true
                            )
                        }
                    }
                    is Response.Success -> {
                        _notesUiState.update {currentState ->
                           currentState.copy(
                               notes = responese.data,
                               loading = false
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
            noteRepository.signOut()
            onSuccess()
        }
    }



}


data class NotesUiState(
    val notes: Map<LocalDate,List<Note>> = emptyMap(),
    val error: String = "",
    val loading: Boolean = false
)