package com.dscoding.offlinecaching.domain.repository

import com.dscoding.offlinecaching.data.model.SimpsonsQuote
import com.dscoding.offlinecaching.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SimpsonsQuotesRepository {

    fun getSimpsonsQuotes(forceNetworkRefresh: Boolean): Flow<Resource<List<SimpsonsQuote>>>
}
