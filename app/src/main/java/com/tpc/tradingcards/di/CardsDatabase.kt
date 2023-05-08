package com.tpc.tradingcards.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tpc.tradingcards.data.dao.CardDao
import com.tpc.tradingcards.data.dao.CardSetDao
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet

@Database(entities = [Card::class, CardSet::class], version = 1)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun provideCardDao(): CardDao
    abstract fun provideCardSetDao(): CardSetDao
}