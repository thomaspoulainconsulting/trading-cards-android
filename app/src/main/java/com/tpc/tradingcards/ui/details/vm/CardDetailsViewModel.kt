package com.tpc.tradingcards.ui.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.details.state.CardDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val repository: PokemonCardRepository,
) : ViewModel() {

    private var _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState.Loading)
    val state = _state.asStateFlow()

    fun getCards(idSet: String) = viewModelScope.launch {
        try {
            _state.tryEmit(CardDetailsState.Loading)

            val cardTypes = repository.getCardTypes().let {
                it.ifEmpty {
                    repository.fetchCardTypes()
                    repository.getCardTypes()
                }
            }

            repository.getCards(idSet).let {
                it.ifEmpty {
                    repository.fetchCards(idSet)
                    repository.getCards(idSet)
                }
            }.let {cards ->
                _state.tryEmit(CardDetailsState.Success(cards, cardTypes))
            }
        } catch (e: Throwable) {
            _state.tryEmit(CardDetailsState.Error(e))
        }
    }

}