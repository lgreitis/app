package com.lukasgreicius.app

import Note
import NoteViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp

class DeleteNoteActivity : ComponentActivity() {
    private val viewModel: NoteViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.readData();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeleteNoteScreen(viewModel)
        }
    }
}

@Composable
fun DeleteNoteScreen(viewModel: NoteViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val notes by viewModel.notesLiveData.observeAsState(listOf())
    var selectedNoteTitle by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
        ) {
            Button(onClick = { expanded = !expanded }) {
                Text(text = selectedNoteTitle.ifBlank { "Select a note" })
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                notes.forEach { note ->
                    DropdownMenuItem(
                        text = { Text("${note.title}") },
                        onClick = {
                            selectedNoteTitle = note.title;
                            expanded = false;
                        })
                }
            }
        }
        Button(onClick = {
            coroutineScope.launch {
                viewModel.deleteNoteByTitle(selectedNoteTitle);
                viewModel.readData();
                selectedNoteTitle = ""
            }
        }) {
            Text("Delete")
        }
    }
}
