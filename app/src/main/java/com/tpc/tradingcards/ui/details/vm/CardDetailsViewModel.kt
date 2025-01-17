package com.tpc.tradingcards.ui.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.details.state.CardDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CardDetailsViewModel(
    private val repository: PokemonCardRepository,
) : ViewModel() {

    val state: StateFlow<CardDetailsState>
        field: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState.Loading)

    val selectedTypes: StateFlow<Map<String, Boolean>>
        field: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(emptyMap())

    private lateinit var idSet: String

    init {
        getCardTypes()
    }

    fun setIdSet(idSet: String) {
        this.idSet = idSet
    }

    private fun getCardTypes() = viewModelScope.launch {
        runCatching {
            val cardTypes = repository.getCardTypes()
            selectedTypes.value = cardTypes.map { it.name }.associateWith { true }
        }.onFailure { e ->
            Timber.e(e)
        }
    }

    fun getCards() = viewModelScope.launch {
        runCatching {
            state.value = CardDetailsState.Loading

            val cards = repository.getCards(idSet)

            state.value = CardDetailsState.Success(cards)
        }.onFailure { e ->
            state.value = CardDetailsState.Error(e)
        }
    }

    fun updateSelectedType(cardType: String) {
        selectedTypes.value = selectedTypes.value.toMutableMap().apply {
            this[cardType] = !this[cardType]!!
        }

        getCards()
    }

}