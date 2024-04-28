package com.tpc.tradingcards.core.di

import android.app.Application
import androidx.room.Room
import com.tpc.tradingcards.core.di.CardsDatabase
import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import com.tpc.tradingcards.data.dao.CardTypeDao
import org.koin.dsl.module

fun provideDatabase(app: Application): CardsDatabase =
    Room.databaseBuilder(
        app.applicationContext,
        CardsDatabase::class.java, "cards-database"
    )
        .fallbackToDestructiveMigration()
        .build()

fun provideCardDao(database: CardsDatabase): CardDao = database.provideCardDao()
fun provideCardSetDao(database: CardsDatabase): CardSetDao = database.provideCardSetDao()
fun provideCardTypeDao(database: CardsDatabase): CardTypeDao = database.provideCardTypeDao()

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideCardDao(get()) }
    single { provideCardSetDao(get()) }
    single { provideCardTypeDao(get()) }
}