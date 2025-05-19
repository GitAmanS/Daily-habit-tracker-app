package com.example.daily_habit_tracker_app.di

import android.content.Context
import androidx.room.Room
import com.example.daily_habit_tracker_app.data.local.HabitDao
import com.example.daily_habit_tracker_app.data.local.HabitDatabase
import com.example.daily_habit_tracker_app.data.repository.HabitRepositoryImpl
import com.example.daily_habit_tracker_app.domain.repository.HabitRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext appContext: Context): HabitDatabase {
            return Room.databaseBuilder(
                appContext,
                HabitDatabase::class.java,
                "habit_database"
            ).build()
        }

        @Provides
        fun provideHabitDao(db: HabitDatabase): HabitDao {
            return db.habitDao()
        }
    }

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        impl: HabitRepositoryImpl
    ): HabitRepository
}