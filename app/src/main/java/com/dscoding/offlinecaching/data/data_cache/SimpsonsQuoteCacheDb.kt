package com.dscoding.offlinecaching.data.data_cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dscoding.offlinecaching.data.model.SimpsonsQuote

@Database(entities = [SimpsonsQuote::class], version = 1)

abstract class SimpsonsQuoteCacheDb : RoomDatabase() {
    abstract val simpsonQuoteCacheDao: SimpsonsQuoteCacheDao
}