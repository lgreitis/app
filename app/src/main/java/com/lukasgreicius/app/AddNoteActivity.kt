package com.lukasgreicius.app

import Note
import NoteViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp

class AddNoteActivity : ComponentActivity() {
    private val viewModel: NoteViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.readData();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AddNoteScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(viewModel: NoteViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") }
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") }
        )
        Button(onClick = {
            coroutineScope.launch {
                viewModel.addNote(Note(title, content));
                Toast.makeText(context, "Note added!", Toast.LENGTH_LONG).show()
            }
        }) {
            Text("Add")
        }
    }
}
