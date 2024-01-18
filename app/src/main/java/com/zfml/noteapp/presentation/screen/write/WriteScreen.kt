package com.zfml.noteapp.presentation.screen.write

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.painterResource
import com.zfml.noteapp.R
import com.zfml.noteapp.domain.model.Note

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteScreen(
    onNavigateToHome: () -> Unit,
    createdDate: Long?,
    noteTitle: State<NoteTextFieldState>,
    noteDescription: State<NoteTextFieldState>,
    onTitleChanged: (String) -> Unit,
    onTitleFocusChanged: (FocusState) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDescriptionFocusChanged: (FocusState) -> Unit,
    onDeleteNote: () -> Unit,
    onSaveNote: () -> Unit
) {

    Scaffold(
        topBar = {
            WriteTopBar(
                createdDate = createdDate,
                onDateChange = { /*TODO*/ },
                onNavigateToHome = onNavigateToHome,
                onDeleteNote = onDeleteNote
            )
        },
        floatingActionButton = {
           FloatingActionButton(onClick = onSaveNote) {
               Icon(painterResource(id = R.drawable.baseline_save_24), contentDescription =" Create Icon" )
           }
        }
        ,
        content = { padding ->
            WriteContent(
            padding = padding,
            noteTitle = noteTitle,
            noteDescription = noteDescription ,
            onTitleChanged = onTitleChanged,
            onTitleFocusChanged =  onTitleFocusChanged,
            onDescriptionChanged = onDescriptionChanged,
            onDescriptionFocusChanged =  onDescriptionFocusChanged
                )
        }

    )

}