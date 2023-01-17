package com.dscoding.offlinecaching.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.offlinecaching.data.model.SimpsonsQuote
import com.dscoding.offlinecaching.domain.repository.SimpsonsQuotesRepository
import com.dscoding.offlinecaching.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SimpsonsQuoteListViewModel @Inject constructor(private val repository: SimpsonsQuotesRepository) :
    ViewModel() {

    private val _state = mutableStateOf(SimpsonsQuotesState())
    val state: State<SimpsonsQuotesState> = _state

    private var getSimpsonsQuotesJob: Job? = null

    init {
        getSimpsonsQuoteList()
    }

    fun getSimpsonsQuoteList(forceRefresh: Boolean = false) {
        getSimpsonsQuotesJob?.cancel()
        getSimpsonsQuotesJob = repository.getSimpsonsQuotes(forceNetworkRefresh = forceRefresh)
            .onEach { simpsonsQuotesResource ->
                when (simpsonsQuotesResource) {
                    is Resource.Loading ->
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    is Resource.Success ->
                        _state.value = state.value.copy(
                            simpsonsQuotes = simpsonsQuotesResource.data ?: emptyList(),
                            isLoading = false
                        )
                    is Resource.Error ->
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                }
            }
            .launchIn(viewModelScope)
    }
}

data class SimpsonsQuotesState(
    val simpsonsQuotes: List<SimpsonsQuote> = emptyList(),
    val isLoading: Boolean = true
)
