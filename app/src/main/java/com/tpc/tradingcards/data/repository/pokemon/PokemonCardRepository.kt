package com.tpc.tradingcards.data.repository.pokemon

import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.data.service.PokemonTradingCardService
import javax.inject.Inject

class PokemonCardRepository @Inject constructor(
    private val localCardSource: CardDao,
    private val localCardSetSource: CardSetDao,
    private val remoteSource: PokemonTradingCardService,
) {

    fun getCards(idSet: String) = localCardSource.getAllCards(CardType.POKEMON, idSet)

    fun getSets() = localCardSetSource.getCardSetsWithType(CardType.POKEMON)

    suspend fun fetchCards(idSet: String) {
        val cards = remoteSource.getPokemonCards(
            query = "!set.id:$idSet",
            orderBy = "nationalPokedexNumbers",
            select = "id,images,name,number,nationalPokedexNumbers,supertype"
        ).data.map {
            Card(
                id = it.id,
                name = it.name,
                urlSmall = it.images.small,
                urlLarge = it.images.large,
                number = it.number.toIntOrNull() ?: -1,
                idSet = idSet,
                cardType = CardType.POKEMON
            )
        }
        localCardSource.insertAll(cards)
    }

    suspend fun fetchCardSets() {
        val sets = remoteSource.getPokemonCardSets().data.map {
            CardSet(
                id = it.id,
                name = it.name,
                cardType = CardType.POKEMON,
                symbol = it.images.symbol,
            )
        }
        localCardSetSource.insertAll(sets)
    }
}