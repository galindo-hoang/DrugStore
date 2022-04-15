package com.example.drugstore.di

import android.content.Context
import com.example.drugstore.R
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.presentation.BaseActivity
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
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context
    ): GoogleSignInClient =
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.resources.getString(R.string.client_id))
                .requestEmail()
                .build()
        )

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseClass(googleSignInClient: GoogleSignInClient): FirebaseClass =
        FirebaseClass(googleSignInClient)
}