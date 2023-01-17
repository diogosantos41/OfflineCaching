package com.dscoding.offlinecaching.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dscoding.offlinecaching.data.model.SimpsonsQuote.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class SimpsonsQuote(
    @PrimaryKey val id: Int? = null,
    val quote: String,
    val character: String,
    val image: String,
    val characterDirection: String
) {
    companion object {
        const val TABLE_NAME = "simplesQuote"
    }
}