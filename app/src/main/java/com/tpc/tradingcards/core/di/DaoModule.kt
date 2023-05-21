package com.tpc.tradingcards.core.di

import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideCardDao(database: CardsDatabase): CardDao = database.provideCardDao()

    @Provides
    fun provideCardSetDao(database: CardsDatabase): CardSetDao = database.provideCardSetDao()

}