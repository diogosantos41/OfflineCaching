package com.dscoding.offlinecaching.data.repository

import androidx.room.withTransaction
import com.dscoding.offlinecaching.data.api.SimpsonsQuotesApi
import com.dscoding.offlinecaching.data.data_cache.SimpsonsQuoteCacheDb
import com.dscoding.offlinecaching.data.model.SimpsonsQuote
import com.dscoding.offlinecaching.domain.repository.SimpsonsQuotesRepository
import com.dscoding.offlinecaching.utils.Resource
import com.dscoding.offlinecaching.utils.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class SimpsonsQuoteRepositoryImpl(
    private val api: SimpsonsQuotesApi,
    private val cacheDb: SimpsonsQuoteCacheDb
) : SimpsonsQuotesRepository {
    private val cacheDao = cacheDb.simpsonQuoteCacheDao
    override fun getSimpsonsQuotes(forceNetworkRefresh: Boolean): Flow<Resource<List<SimpsonsQuote>>> =
        networkBoundResource(
            fromCache = {
                cacheDao.getAllSimpsonQuotes()
            },
            fromNetwork = {
                // I added this delay because it's easier to see when
                // it's loading from cache VS. loading from network
                delay(2000)
                api.getSimpsonsQuotes()
            },
            saveOnCache = { simpsonQuote ->
                cacheDb.withTransaction {
                    cacheDao.deleteAllSimpsonQuotes()
                    cacheDao.insertSimpsonQuotes(simpsonQuote)
                }
            },
            forceNetworkRefresh = { simpsonQuotes ->
                forceNetworkRefresh || simpsonQuotes.isEmpty()
            }
        )
}
