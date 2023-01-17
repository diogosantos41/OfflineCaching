package com.dscoding.offlinecaching.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.dscoding.offlinecaching.data.model.SimpsonsQuote

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpsonsQuoteListScreen(
    viewModel: SimpsonsQuoteListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val pullRefreshState = rememberPullRefreshState(state.isLoading,
        { viewModel.getSimpsonsQuoteList(forceRefresh = true) })

    Box(
        Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (state.simpsonsQuotes.isEmpty() && !state.isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "There is no data to show")
                Text(text = "Pull to Refresh!")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(state.simpsonsQuotes) { simpsonsQuote ->
                SimpsonsQuoteItem(simpsonsQuote = simpsonsQuote, onClick = { })
            }
        }

        PullRefreshIndicator(state.isLoading, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun SimpsonsQuoteItem(simpsonsQuote: SimpsonsQuote, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 5.dp),
        backgroundColor = Color(0xfff8db27),
        elevation = 4.dp,
        contentColor = Color.Black
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(simpsonsQuote.image),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(60.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = simpsonsQuote.quote, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = simpsonsQuote.character, fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}