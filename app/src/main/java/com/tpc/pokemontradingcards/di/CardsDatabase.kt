package com.tpc.pokemontradingcards.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tpc.pokemontradingcards.data.dao.CardDao
import com.tpc.pokemontradingcards.data.dao.CardSetDao
import com.tpc.pokemontradingcards.data.model.Card
import com.tpc.pokemontradingcards.data.model.CardSet

@Database(entities = [Card::class, CardSet::class], version = 1)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun provideCardDao(): CardDao
    abstract fun provideCardSetDao(): CardSetDao
}