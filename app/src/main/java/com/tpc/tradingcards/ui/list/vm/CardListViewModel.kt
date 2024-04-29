package com.tpc.tradingcards.ui.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.list.state.CardListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CardListViewModel(
    private val cardRepository: PokemonCardRepository
) : ViewModel() {

    private var _state: MutableStateFlow<CardListState> = MutableStateFlow(CardListState.Loading)
    val state = _state.asStateFlow()

    init {
        fetchCardTypes()
        fetchCardSets()
    }

    private fun fetchCardSets() = viewModelScope.launch {
        try {
            val sets = cardRepository.getSets().let {
                it.ifEmpty {
                    cardRepository.fetchCardSets()
                    cardRepository.getSets()
                }
            }
            _state.tryEmit(CardListState.Success(sets))
        } catch (e: Exception) {
            _state.tryEmit(CardListState.Error(e))
        }
    }

    private fun fetchCardTypes()  = viewModelScope.launch {
        try {
            cardRepository.fetchCardTypes()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}