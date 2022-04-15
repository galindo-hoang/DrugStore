package com.example.drugstore.di

import android.content.Context
import com.example.drugstore.R
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.UserRepo
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.service.AuthService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideBaseActivity(@ActivityContext context: Context): BaseActivity =
        context as BaseActivity

    @Provides
    fun provideAuthService(
        context: BaseActivity,
        userRepo: UserRepo,
        authRepo: AuthRepo
    ): AuthService =
        AuthService(context, userRepo, authRepo)
}
