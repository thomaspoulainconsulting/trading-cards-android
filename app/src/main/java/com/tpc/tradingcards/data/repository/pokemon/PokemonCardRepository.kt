package com.tpc.tradingcards.data.repository.pokemon

import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import com.tpc.tradingcards.data.dao.CardTypeDao
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.data.model.TradingCardGame
import com.tpc.tradingcards.data.service.PokemonTradingCardService

class PokemonCardRepository(
    private val localCardSource: CardDao,
    private val localCardSetSource: CardSetDao,
    private val localCardTypeSource: CardTypeDao,
    private val remoteSource: PokemonTradingCardService,
) {
    /**
     * LOCAL
     */

    suspend fun getCards(idSet: String) = localCardSource.get(TradingCardGame.POKEMON, idSet)

    suspend fun getSets() = localCardSetSource.get(TradingCardGame.POKEMON)

    suspend fun getCardTypes() = localCardTypeSource.get(TradingCardGame.POKEMON)

    suspend fun updateCardType(cardType: CardType) = localCardTypeSource.update(cardType)
    
    /**
     * REMOTE
     */

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
                supertype = it.supertype,
                tradingCardGame = TradingCardGame.POKEMON
            )
        }
        localCardSource.insert(cards)
    }

    suspend fun fetchCardSets() {
        val sets = remoteSource.getPokemonCardSets().data.map {
            CardSet(
                id = it.id,
                name = it.name,
                tradingCardGame = TradingCardGame.POKEMON,
                symbol = it.images.symbol,
                releaseDate = it.releaseDate
            )
        }
        localCardSetSource.insert(sets)
    }

    suspend fun fetchCardTypes() {
        val types = remoteSource.getPokemonCardTypes().data.map { supertype ->
            CardType(
                name = supertype,
                tradingCardGame = TradingCardGame.POKEMON
            )
        }
        localCardTypeSource.insert(types)
    }


}