package com.tpc.tradingcards.core.di

import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::PokemonCardRepository)
}