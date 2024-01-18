package com.zfml.noteapp.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zfml.noteapp.R
import com.zfml.noteapp.data.repository.NoteRepositoryImpl
import com.zfml.noteapp.domain.repository.NoteRepository
import com.zfml.noteapp.util.Constants
import com.zfml.noteapp.util.Constants.FIREBASE_COLLECTION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

 @Provides
 @Singleton
 fun provideNotesRef() = Firebase.firestore.collection(FIREBASE_COLLECTION)

@Provides
@Singleton
fun provideNoteRepository(notesRef: CollectionReference): NoteRepository = NoteRepositoryImpl(notesRef)





}