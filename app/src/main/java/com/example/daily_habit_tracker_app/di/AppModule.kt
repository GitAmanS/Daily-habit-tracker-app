package com.example.daily_habit_tracker_app.di

import com.example.daily_habit_tracker_app.data.repository.AuthRepositoryImpl
import com.example.daily_habit_tracker_app.data.repository.HabitRepositoryImpl
import com.example.daily_habit_tracker_app.domain.repository.AuthRepository
import com.example.daily_habit_tracker_app.domain.repository.HabitRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
    }

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        impl: HabitRepositoryImpl
    ): HabitRepository
}
