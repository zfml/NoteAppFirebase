package com.zfml.noteapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    name: String?
) {
    Column {
        Text(
            text = name ?: "Nothing"
        )
        Button(onClick = onSignOut) {
            Text(text = "Sign Out")
        }
    }

}