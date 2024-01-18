package com.zfml.noteapp.presentation.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zfml.noteapp.domain.model.Note
import com.zfml.noteapp.domain.repository.Notes
import com.zfml.noteapp.presentation.components.DateHeader
import com.zfml.noteapp.presentation.components.EmptyPage
import com.zfml.noteapp.presentation.components.NoteHolder
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent (
    padding: PaddingValues,
    notes: Notes,
    onClick: (String) -> Unit
) {
        if(notes.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding())
                ) {
                    notes.forEach{( localDate, notes )->
                        stickyHeader(key = localDate ){
                            DateHeader(localDate = localDate)
                        }
                        items(
                            items = notes,
                            key = {it.id}
                        ) { note ->

                            NoteHolder(
                                note = note,
                                onClick = onClick
                            )

                        }
                    }

                }
            }else {
                EmptyPage()
        }

}