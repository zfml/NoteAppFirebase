package com.zfml.noteapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zfml.noteapp.navigation.Screen
import com.zfml.noteapp.navigation.SetupNavGraph
import com.zfml.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController ,
                    startDestination = getStartDestination()
                )
            }
        }
    }

    private fun getStartDestination(): String {
        val user = Firebase.auth.currentUser
       return if(user != null) Screen.Home.route
        else Screen.Authentication.route
    }

}

