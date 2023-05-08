package com.tpc.tradingcards.di

import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import com.tpc.tradingcards.data.repository.CardRepository
import com.tpc.tradingcards.data.repository.PokemonCardRepository
import com.tpc.tradingcards.data.service.PokemonTradingCardService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    fun providePokemonCardRepository(
        cardDao: CardDao,
        cardSetDao: CardSetDao,
        pokemonTradingCardService: PokemonTradingCardService
    ): CardRepository = PokemonCardRepository(cardDao, cardSetDao, pokemonTradingCardService)
}