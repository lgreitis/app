package com.lukasgreicius.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lukasgreicius.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    var text by remember { mutableStateOf("") }
    var textColor by remember { mutableStateOf(Color.Unspecified) }
    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        textColor = colorScheme.onSurface
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = textColor,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            text = "Hello, world!"
        }) {
            Text("Button")
        }
        if (text !== "") {
            Button(onClick = {
                textColor = colorScheme.error
            }) {
                Text("Now make it red!")
            }
        }
    }
}