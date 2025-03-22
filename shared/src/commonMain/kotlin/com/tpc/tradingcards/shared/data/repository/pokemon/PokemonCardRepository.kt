package com.tpc.tradingcards.shared.data.repository.pokemon

import com.tpc.tradingcards.shared.data.model.Card
import com.tpc.tradingcards.shared.data.model.CardSet
import com.tpc.tradingcards.shared.data.model.CardType
import com.tpc.tradingcards.shared.data.model.TradingCardGame
import com.tpc.tradingcards.shared.data.service.PokemonTradingCardService

class PokemonCardRepository(
    private val remoteSource: PokemonTradingCardService,
) {
    suspend fun getCards(idSet: String): List<Card> =
        remoteSource.getPokemonCards(
            query = "!set.id:$idSet",
            orderBy = "nationalPokedexNumbers",
            select = "id,images,name,number,nationalPokedexNumbers,supertype"
        ).data.map { card ->
            Card(
                id = card.id,
                name = card.name,
                urlSmall = card.images.small,
                urlLarge = card.images.large,
                number = card.number.toIntOrNull() ?: -1,
                idSet = idSet,
                supertype = card.supertype,
                tradingCardGame = TradingCardGame.POKEMON
            )
        }

    suspend fun getSets(): List<CardSet> =
        remoteSource.getPokemonCardSets().data.map {
            CardSet(
                id = it.id,
                name = it.name,
                tradingCardGame = TradingCardGame.POKEMON,
                symbol = it.images.symbol,
                releaseDate = it.releaseDate
            )
        }

    suspend fun getCardTypes(): List<CardType> {
        return remoteSource.getPokemonCardTypes().data.map { supertype ->
            CardType(
                name = supertype,
                tradingCardGame = TradingCardGame.POKEMON
            )
        }
    }
}
