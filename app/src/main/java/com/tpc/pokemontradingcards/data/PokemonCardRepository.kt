package com.tpc.pokemontradingcards.data

import com.tpc.pokemontradingcards.data.dao.ModelCardDao
import com.tpc.pokemontradingcards.data.model.ModelCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonCardRepository @Inject constructor(
    private val localSource: ModelCardDao,
    private val remoteSource: PokemonTradingCardService,
) {

    /**
     * Returns a flow of the pokemon cards in local database
     */
    fun loadPokemonCards(): Flow<List<ModelCard>> = localSource.getAll()

    /**
     * Download a pokemon set if it isn't in database
     */
    suspend fun downloadPokemonSet(idSet: String) {
        // First we check if there is some cards with the idSet in database
        val cardsWithCurrentSet = localSource.getAll(idSet)

        // If cards with the idSet is already there, we do not download it again
        if (cardsWithCurrentSet.isNotEmpty()) return

        // Otherwise, we download the cards and insert them in database
        val cards = remoteSource
            .getPokemonCards(
                query = "!set.id:$idSet supertype:Pokémon",
                orderBy = "nationalPokedexNumbers",
                select = "id,images,name,number,nationalPokedexNumbers,supertype"
            )
            .cards
            .map {
                ModelCard(
                    id = it.id,
                    label = it.name,
                    url = it.images.large,
                    number = it.number.first(),
                    idSet = idSet,
                )
            }
        localSource.insertAll(cards)
    }
}