package com.tpc.tradingcards.core.di

import android.app.Application
import androidx.room.Room
import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import com.tpc.tradingcards.data.dao.CardTypeDao
import org.koin.dsl.module

private fun provideDatabase(app: Application): CardsDatabase =
    Room.databaseBuilder(
        app.applicationContext,
        CardsDatabase::class.java, "trading-cards"
    )
        .fallbackToDestructiveMigration()
        .build()

private fun provideCardDao(database: CardsDatabase): CardDao = database.provideCardDao()
private fun provideCardSetDao(database: CardsDatabase): CardSetDao = database.provideCardSetDao()
private fun provideCardTypeDao(database: CardsDatabase): CardTypeDao = database.provideCardTypeDao()

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideCardDao(get()) }
    single { provideCardSetDao(get()) }
    single { provideCardTypeDao(get()) }
}