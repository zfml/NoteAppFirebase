package com.zfml.noteapp.presentation.screen.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zfml.noteapp.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {


    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn.asStateFlow()

    var authenticated  = mutableStateOf(false)
        private set

    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    init {
        // On VM init, immediately check Firebase’s persisted session
        viewModelScope.launch {
            val user = noteRepository.getCurrentUser()
            _isUserLoggedIn.value = user != null
        }
    }


    fun signInWithGoogle(
        tokenId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

                withContext(Dispatchers.IO) {
                  noteRepository.signInWithTokenId(tokenId,
                      onSuccess = {
                          authenticated.value = true

                      },
                      onError =  {
                          onError(it)
                      }
                      )
                }

        }
    }

}

