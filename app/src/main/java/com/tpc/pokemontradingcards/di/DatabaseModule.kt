package com.tpc.pokemontradingcards.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CardsDatabase =
        Room.databaseBuilder(
            app.applicationContext,
            CardsDatabase::class.java, "cards-database"
        ).build()
}