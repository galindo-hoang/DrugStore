package com.example.drugstore.di

import android.content.Context
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.UserRepo
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.service.AuthService
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideUserRepo(): UserRepo = UserRepo()

    @Provides
    fun provideAuthRepo(
        googleSignInClient: GoogleSignInClient,
        firebaseAuth: FirebaseAuth
    ): AuthRepo =
        AuthRepo(googleSignInClient, firebaseAuth)
}
