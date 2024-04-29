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

    private var _selectedTypes: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(emptyMap())
    val selectedTypes = _selectedTypes.asStateFlow()

    private lateinit var idSet: String

    init {
        getCardTypes()
    }

    fun setIdSet(idSet: String) {
        this.idSet = idSet
    }

    private fun getCardTypes() = viewModelScope.launch {
        val cardTypes = repository.getCardTypes().let {
            it.ifEmpty {
                repository.fetchCardTypes()
                repository.getCardTypes()
            }
        }
        _selectedTypes.tryEmit(cardTypes.map { it.name }.associateWith { true })
    }

    fun getCards() = viewModelScope.launch {
        try {
            _state.tryEmit(CardDetailsState.Loading)

            val cardTypes = selectedTypes.value.filter { it.value }.map { it.key }

            repository.getCards(idSet, cardTypes).let {
                it.ifEmpty {
                    repository.fetchCards(idSet)
                    repository.getCards(idSet, cardTypes)
                }
            }.let { cards ->
                _state.tryEmit(CardDetailsState.Success(cards))
            }
        } catch (e: Throwable) {
            _state.tryEmit(CardDetailsState.Error(e))
        }
    }

    fun updateSelectedType(cardType: String) {
        val selectedCardValue = selectedTypes.value.toMutableMap().apply {
            this[cardType] = !this[cardType]!!
        }

        _selectedTypes.tryEmit(selectedCardValue)

        // reload
        getCards()
    }

}