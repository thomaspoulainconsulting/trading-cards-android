package com.tpc.tradingcards.core.di

import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
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