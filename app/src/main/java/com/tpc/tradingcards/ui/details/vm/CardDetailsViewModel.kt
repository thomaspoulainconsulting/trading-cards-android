package com.tpc.tradingcards.ui.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.details.state.CardDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val repository : PokemonCardRepository,
)  : ViewModel() {

    private var _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState.Loading)
    val state = _state.asStateFlow()

    fun getCards(idSet: String) = viewModelScope.launch {
        try {
            _state.tryEmit(CardDetailsState.Loading)

            val cards = repository.getCards(idSet).let {
                if (it.isEmpty()) {
                    repository.fetchCards(idSet)
                    repository.getCards(idSet)
                }
                it
            }
            _state.tryEmit(CardDetailsState.Success(cards))

        } catch (e: Throwable) {
            _state.tryEmit(CardDetailsState.Error(e))
        }
    }

}