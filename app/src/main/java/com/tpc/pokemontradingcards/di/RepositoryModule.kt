package com.tpc.pokemontradingcards.di

import com.tpc.pokemontradingcards.data.dao.CardDao
import com.tpc.pokemontradingcards.data.dao.CardSetDao
import com.tpc.pokemontradingcards.data.repository.CardRepository
import com.tpc.pokemontradingcards.data.repository.PokemonCardRepository
import com.tpc.pokemontradingcards.data.service.PokemonTradingCardService
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