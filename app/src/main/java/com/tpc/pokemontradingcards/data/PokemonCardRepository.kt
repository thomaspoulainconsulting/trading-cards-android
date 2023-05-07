package com.tpc.pokemontradingcards.data

import com.tpc.pokemontradingcards.CardRepository
import com.tpc.pokemontradingcards.data.dao.CardDao
import com.tpc.pokemontradingcards.data.dao.CardSetDao
import com.tpc.pokemontradingcards.data.model.Card
import com.tpc.pokemontradingcards.data.model.CardSet
import com.tpc.pokemontradingcards.data.model.CardType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonCardRepository @Inject constructor(
    private val localCardSource: CardDao,
    private val localCardSetSource: CardSetDao,
    private val remoteSource: PokemonTradingCardService,
) : CardRepository {

    override fun loadCards(): Flow<List<Card>> = localCardSource.getAllCards(CardType.POKEMON)

    override fun loadSets(): Flow<List<CardSet>> = localCardSetSource.getAllCardSets()

    override suspend fun fetchCards(idSet: String) {
        // First we check if there is some cards with the idSet in database
        // If cards with the idSet is already there, we do not download it again
        val cardsWithCurrentSet = localCardSource.getAllCards(idSet)
        if (cardsWithCurrentSet.isNotEmpty()) return

        // Otherwise, we download the cards and insert them in database
        val cards = remoteSource
            .getPokemonCards(
                query = "!set.id:$idSet supertype:Pokémon",
                orderBy = "nationalPokedexNumbers",
                select = "id,images,name,number,nationalPokedexNumbers,supertype"
            )
            .data
            .map {
                Card(
                    id = it.id,
                    name = it.name,
                    urlSmall = it.images.small,
                    urlLarge = it.images.large,
                    number = it.nationalPokedexNumbers.first(),
                    idSet = idSet,
                    cardType = CardType.POKEMON
                )
            }
        localCardSource.insertAll(cards)
    }

    override suspend fun fetchCardSets(cardType: CardType) {
        // First we check if there is already sets in database
        val setsInDatabase = localCardSetSource.getCardSetsWithType(cardType)
        if (setsInDatabase.isNotEmpty()) return

        //Otherwise we download the card sets and insert them in database
        val sets = remoteSource
            .getPokemonCardSets()
            .data
            .map {
                CardSet(
                    id = it.id,
                    name = it.name,
                    cardType = CardType.POKEMON,
                    totalCardsInSet = it.total,
                    symbol = it.images.symbol,
                    logo = it.images.logo
                )
            }
        localCardSetSource.insertAll(sets)
    }


    /*
    enum class PokemonSet(val id: String) {
    BASE("base1"),
    JUNGLE("base2"),
    FOSSIL("base3"),
    POKEMON_GO("pgo")
}
     */


}