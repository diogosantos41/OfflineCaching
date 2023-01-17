package com.dscoding.offlinecaching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dscoding.offlinecaching.ui.SimpsonsQuoteListScreen
import com.dscoding.offlinecaching.ui.theme.OfflineCachingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineCachingTheme {
                SimpsonsQuoteListScreen()
            }
        }
    }
}