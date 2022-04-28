package com.example.myrecipeapp.di

import com.example.myrecipeapp.repository.RecipeRepository
import com.example.myrecipeapp.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideRepository(repository: RecipeRepositoryImpl): RecipeRepository

}