package com.tpc.pokemontradingcards.data

import com.tpc.pokemontradingcards.data.dao.ModelCardDao
import com.tpc.pokemontradingcards.data.model.ModelCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonCardRepository @Inject constructor(
    private val remoteSource: PokemonTradingCardService,
    private val localSource: ModelCardDao
) {
    fun getPokemonCards(
        set: PokemonSet
    ): Flow<List<ModelCard>> = flow {

        val cardsFromLocalSource = localSource.getAll()
        if (cardsFromLocalSource.isNotEmpty()) {
            emit(cardsFromLocalSource)
        } else {
            // Retrieve remote cards
            val cards = remoteSource.getPokemonCards(
                query = "!set.id:${set.id} supertype:Pokémon",
                orderBy = "nationalPokedexNumbers",
                select = "id,images,name,number,nationalPokedexNumbers,supertype"
            ).cards.map {
                ModelCard(
                    id = it.id,
                    label = it.name,
                    url = it.images.large,
                    number = it.number.first(),
                )
            }

            // save local cards
            localSource.insertAll(cards)

            emit(cards)
        }
    }
}