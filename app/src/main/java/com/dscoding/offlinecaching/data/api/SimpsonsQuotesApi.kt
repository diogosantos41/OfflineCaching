package com.dscoding.offlinecaching.data.api

import com.dscoding.offlinecaching.data.model.SimpsonsQuote
import retrofit2.http.GET

interface SimpsonsQuotesApi {

    companion object {
        const val BASE_URL = "https://thesimpsonsquoteapi.glitch.me/"
    }

    @GET("quotes?count=8")
    suspend fun getSimpsonsQuotes(): List<SimpsonsQuote>
}