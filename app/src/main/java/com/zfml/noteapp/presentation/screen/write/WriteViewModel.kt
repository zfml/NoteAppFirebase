package com.zfml.noteapp.presentation.screen.write

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.domain.repository.NoteRepository
import com.zfml.noteapp.domain.model.Response
import com.zfml.noteapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var currentNoteId: String? = null
    init {
        getNote()
    }


    var createdDate = mutableStateOf<Long?>(null)
    private set

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter Title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle


    private val _noteDescription = mutableStateOf(NoteTextFieldState(
        hint = "Enter Description.."
    ))

    val noteDescription : State<NoteTextFieldState> = _noteDescription


    fun onChangedTitle(title: String) {
       _noteTitle.value =  _noteTitle.value.copy(
            text = title
        )
    }
    fun onChangedTitleFocus(focusState: FocusState) {
        _noteTitle.value = _noteTitle.value.copy(
            isHintVisible = !focusState.isFocused && _noteTitle.value.text.isBlank()
        )
    }

    fun onChangedDescription(description: String){
        _noteDescription.value = _noteDescription.value.copy(
            text = description
        )
    }

    fun onChangedDescriptionFocus(focusState: FocusState) {
        _noteDescription.value = _noteDescription.value.copy(
            isHintVisible = !focusState.isFocused && _noteTitle.value.text.isBlank()
        )
    }


    private fun getNote() {
        savedStateHandle.get<String>(WRITE_SCREEN_ARGUMENT_KEY).let {noteId ->
            if(noteId != null) {
                viewModelScope.launch {
                    when(val result = noteRepository.getNoteById(noteId)) {
                        is Response.Error -> TODO()
                        Response.Loading -> TODO()
                        is Response.Success -> {
                            result.data.let { note ->
                                currentNoteId = note.id
                                _noteTitle.value = _noteTitle.value.copy(
                                    text = note.title ,
                                    isHintVisible = false
                                )
                                _noteDescription.value = _noteDescription.value.copy(
                                    text = note.description,
                                    isHintVisible = false
                                )
                                createdDate.value = note.createdDate
                            }
                        }
                    }
                }
            }

        }
    }
    fun saveNote(
        onSuccess: () -> Unit,
        onError:(String) -> Unit
    ) {
        if(currentNoteId != null) {
            updateNote(onSuccess,onError)
        } else {
            addNote(onSuccess,onError)
        }
    }

    fun isValidInput(title:String = _noteTitle.value.text , description: String = _noteDescription.value.text):Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }


    private fun addNote(
        onSuccess: () -> Unit,
        onError:(String) -> Unit
    ) {
        viewModelScope.launch {
            val note = Note(
                title = _noteTitle.value.text,
                description = _noteDescription.value.text
            )
            when(val result =  noteRepository.addNote(note)) {
                is Response.Error -> onError(result.error.toString())
                Response.Loading -> TODO()
                is Response.Success -> onSuccess()
            }
        }
    }

    private fun updateNote(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            val note = Note(
                id = currentNoteId!!,
                title = _noteTitle.value.text,
                description = _noteDescription.value.text,
                createdDate = createdDate.value!!
            )
            when(val result = noteRepository.updateNote(note)) {
                is Response.Error -> onError(result.error.toString())
                Response.Loading -> TODO()
                is Response.Success -> onSuccess()
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            noteRepository.deleteNote(currentNoteId!!)
        }
    }






}