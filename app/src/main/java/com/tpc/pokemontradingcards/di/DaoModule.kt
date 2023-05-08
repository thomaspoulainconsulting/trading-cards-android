package com.tpc.pokemontradingcards.di

import com.tpc.pokemontradingcards.data.dao.CardDao
import com.tpc.pokemontradingcards.data.dao.CardSetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    @Singleton
    fun provideCardDao(database: CardsDatabase): CardDao = database.provideCardDao()

    @Provides
    @Singleton
    fun provideCardSetDao(database: CardsDatabase): CardSetDao = database.provideCardSetDao()

}