package com.tpc.tradingcards.shared.core.di

import com.tpc.tradingcards.shared.data.repository.pokemon.PokemonCardRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::PokemonCardRepository)
}