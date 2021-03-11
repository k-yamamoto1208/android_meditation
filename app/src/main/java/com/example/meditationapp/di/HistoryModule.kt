package com.example.meditationapp.di

import android.content.Context
import androidx.room.Room
import com.example.meditationapp.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "history_db").build()

    @Singleton
    @Provides
    fun provideHistoryDao(db: AppDatabase) = db.historyDao()
}