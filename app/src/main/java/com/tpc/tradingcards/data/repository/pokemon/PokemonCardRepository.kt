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

    suspend fun getCards(idSet: String, superTypes: List<String>): List<Card> {
        return localCardSource.get(TradingCardGame.POKEMON, idSet, superTypes).let {
            it.ifEmpty {
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
                }.let { cards ->
                    localCardSource.insert(cards)
                    cards
                }
            }
        }
    }

    suspend fun getSets(): List<CardSet> {
        return localCardSetSource.get(TradingCardGame.POKEMON).let {
            it.ifEmpty {
                remoteSource.getPokemonCardSets().data.map {
                    CardSet(
                            id = it.id,
                            name = it.name,
                            tradingCardGame = TradingCardGame.POKEMON,
                            symbol = it.images.symbol,
                            releaseDate = it.releaseDate
                    )
                }.let { sets ->
                    localCardSetSource.insert(sets)
                    sets
                }
            }
        }
    }

    suspend fun getCardTypes(): List<CardType> {
        return localCardTypeSource.get(TradingCardGame.POKEMON).let {
            it.ifEmpty {
                remoteSource.getPokemonCardTypes().data.map { supertype ->
                    CardType(
                            name = supertype,
                            tradingCardGame = TradingCardGame.POKEMON
                    )
                }.let { types ->
                    localCardTypeSource.insert(types)
                    types
                }
            }
        }
    }
}