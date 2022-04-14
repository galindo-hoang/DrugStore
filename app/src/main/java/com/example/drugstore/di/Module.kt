package com.example.drugstore.di

import com.example.drugstore.data.repository.UserRepo
import com.example.drugstore.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object Module {

    @Provides
    fun provideUserRepo(): UserRepo = UserRepo()

    @Provides
    fun provideUserService(userRepo: UserRepo): UserService = UserService(userRepo)
}