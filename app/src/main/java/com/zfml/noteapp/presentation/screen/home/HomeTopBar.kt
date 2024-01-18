package com.zfml.noteapp.presentation.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    openDrawerClicked: () -> Unit
) {
    TopAppBar(
        scrollBehavior = topAppBarScrollBehavior,
        navigationIcon = {
             IconButton(onClick = openDrawerClicked) {
                 Icon(
                     imageVector = Icons.Default.Menu,
                     contentDescription = " Menu Icon"
                 )
             }
        },
        title = {
            Text(
                text = "Note",
            )
        },
        actions = {

        }
    )
}