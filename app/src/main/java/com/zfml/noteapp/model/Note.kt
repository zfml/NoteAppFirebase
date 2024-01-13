package com.zfml.noteapp.model

import com.google.firebase.firestore.ServerTimestamp
import com.zfml.noteapp.ui.theme.*
import java.util.Date

data class Note(
    val title: String,
    val description: String,
    val color: Int,
    @ServerTimestamp
    val date : Date = Date()
) {
    companion object {
        val noteColors = listOf(RedOrange,LightGreen,Violet,BabyBlue,RedPink)
    }

}