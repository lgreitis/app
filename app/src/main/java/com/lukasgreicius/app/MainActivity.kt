package com.lukasgreicius.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var displayText by remember { mutableStateOf("") }
    var countVariant by remember { mutableStateOf(CountVariant.WORDS) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = displayText)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
        ) {
            Button(onClick = { expanded = !expanded }) {
                Text(text = countVariant.toString().lowercase().replaceFirstChar { it.uppercase() })
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Words") },
                    onClick = {
                        countVariant = CountVariant.WORDS
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Symbols") },
                    onClick = {
                        countVariant = CountVariant.SYMBOLS
                        expanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (inputText.isEmpty()) {
                Toast.makeText(context, "String is empty!", Toast.LENGTH_SHORT).show()
            } else {
                var count = WordCounter.countWords(inputText, countVariant)
                displayText = when (countVariant) {
                    CountVariant.WORDS -> "Word Count: $count"
                    CountVariant.SYMBOLS -> "Symbol Count: $count"
                }
            }
        }) {
            Text("Submit")
        }
    }
}
