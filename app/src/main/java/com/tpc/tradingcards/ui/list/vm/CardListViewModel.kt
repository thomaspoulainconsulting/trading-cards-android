package com.tpc.tradingcards.ui.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.list.state.CardListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardListViewModel(
    private val cardRepository: PokemonCardRepository
) : ViewModel() {

    private var _state: MutableStateFlow<CardListState> = MutableStateFlow(CardListState.Loading)
    val state = _state.asStateFlow()

    init {
        fetchCardSets()
    }

    private fun fetchCardSets() = viewModelScope.launch {
        try {
            val sets = cardRepository.getSets()
            _state.tryEmit(CardListState.Success(sets))
        } catch (e: Throwable) {
            _state.tryEmit(CardListState.Error(e))
        }
    }
}