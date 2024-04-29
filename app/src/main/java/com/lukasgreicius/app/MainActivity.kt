package com.lukasgreicius.app

import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val dataLoader = DataLoader()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesList(dataLoader)
        }
    }

}

@Composable
fun NotesList(
    dataLoader: DataLoader
) {
    val coroutineScope = rememberCoroutineScope()
    var data: OpenExchangeRatesResponse? by remember { mutableStateOf(null) }

    val rateList = data?.rates?.entries?.toMutableList()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            data =
                dataLoader.makeGetRequest(Constants.OPEN_EXCHANGE_RATES_REQUEST_URL + "?app_id=${Constants.OPEN_EXCHANGE_RATES_APP_ID}&base=${Constants.BASE_CURRENCY}")
        }
    }

    Column {
        LazyColumn {
            if (rateList != null) {
                items(rateList) { rate ->
                    Column(Modifier.padding(8.dp)) {
                        Text(text = "${rate.key} - ${rate.value}", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}
