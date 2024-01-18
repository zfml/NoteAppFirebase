package com.zfml.noteapp.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import com.zfml.noteapp.ui.theme.*
import java.util.Date

data class Note(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val color: Int? = null,
    val createdDate: Long = System.currentTimeMillis(),
)
{
    companion object {
        val noteColors = listOf(RedOrange,LightGreen,Violet,BabyBlue,RedPink)
    }

}