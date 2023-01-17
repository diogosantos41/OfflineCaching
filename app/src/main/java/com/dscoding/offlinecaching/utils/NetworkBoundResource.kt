package com.dscoding.offlinecaching.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline fromCache: () -> Flow<ResultType>,
    crossinline fromNetwork: suspend () -> RequestType,
    crossinline saveOnCache: suspend (RequestType) -> Unit,
    crossinline forceNetworkRefresh: (ResultType) -> Boolean = { true },
) = flow {
    val data = fromCache().first()

    val flow = if (forceNetworkRefresh(data)) {
        emit(Resource.Loading(data))
        try {
            saveOnCache(fromNetwork())
            fromCache().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            fromCache().map { Resource.Error(throwable, it) }
        }
    } else {
        fromCache().map { Resource.Success(it) }
    }

    emitAll(flow)
}
