package com.zfml.noteapp.presentation.screen.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel: ViewModel() {

    var authenticated  = mutableStateOf(false)
        private set

    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    fun signInWithGoogle(
        tokenId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

                withContext(Dispatchers.IO) {
                    val auth = Firebase.auth
                    val firebaseCredential = GoogleAuthProvider.getCredential(tokenId,null)
                    Log.d("credential",firebaseCredential.toString())
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->

                            if(task.isSuccessful) {
                                onSuccess()
                                authenticated.value = true
                            }else {
                                onError("Authentication Failed")
                            }
                        }
                }

        }
    }

}

