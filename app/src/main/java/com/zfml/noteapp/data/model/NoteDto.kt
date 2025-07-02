package com.zfml.noteapp.data.model

data class NoteDto(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val color: Int? = null,
    val createdDate: Long ,
)
