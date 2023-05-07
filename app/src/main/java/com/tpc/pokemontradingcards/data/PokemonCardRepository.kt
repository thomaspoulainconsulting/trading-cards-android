package com.tpc.pokemontradingcards.data

import com.tpc.pokemontradingcards.CardRepository
import com.tpc.pokemontradingcards.data.dao.CardDao
import com.tpc.pokemontradingcards.data.dao.CardSetDao
import com.tpc.pokemontradingcards.data.model.Card
import com.tpc.pokemontradingcards.data.model.CardSet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonCardRepository @Inject constructor(
    private val localCardSource: CardDao,
    private val localCardSetSource: CardSetDao,
    private val remoteSource: PokemonTradingCardService,
) : CardRepository {

    override fun loadCards(): Flow<List<Card>> = localCardSource.getAllCards()

    override fun loadSets(): Flow<List<CardSet>> = localCardSetSource.getAllCardSets()

    override suspend fun fetchCards(idSet: String) {
        // First we check if there is some cards with the idSet in database
        val cardsWithCurrentSet = localCardSource.getAllCards(idSet)

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
                Card(
                    id = it.id,
                    name = it.name,
                    urlSmall = it.images.small,
                    urlLarge = it.images.large,
                    number = it.number.first(),
                    idSet = idSet,
                )
            }
        localCardSource.insertAll(cards)
    }

    override suspend fun fetchCardSets() {
        TODO("Not yet implemented")
    }


}