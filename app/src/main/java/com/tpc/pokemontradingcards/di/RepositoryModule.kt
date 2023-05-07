package com.tpc.pokemontradingcards.di

import com.tpc.pokemontradingcards.CardRepository
import com.tpc.pokemontradingcards.data.PokemonCardRepository
import com.tpc.pokemontradingcards.data.PokemonTradingCardService
import com.tpc.pokemontradingcards.data.dao.CardDao
import com.tpc.pokemontradingcards.data.dao.CardSetDao
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