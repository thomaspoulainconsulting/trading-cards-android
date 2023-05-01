package com.tpc.pokemontradingcards.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonManager @Inject constructor(
    private val service: PokemonTradingCardService
) {
    fun getPokemonCards(
        set: PokemonSet
    ): Flow<List<PokemonCardData>> = flow {
        emit(
            service.getPokemonCards(
                query = "!set.id:${set.id} supertype:Pokémon",
                orderBy = "nationalPokedexNumbers",
                select = "id,images,name,number,nationalPokedexNumbers,supertype"
            ).cards
        )
    }
}