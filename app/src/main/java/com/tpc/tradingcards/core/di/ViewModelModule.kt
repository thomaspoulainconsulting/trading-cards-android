package com.tpc.tradingcards.core.di

import com.tpc.tradingcards.ui.details.vm.CardDetailsViewModel
import com.tpc.tradingcards.ui.list.vm.CardListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewmodelModule = module {
    viewModelOf(::CardListViewModel)
    viewModelOf(::CardDetailsViewModel)
}