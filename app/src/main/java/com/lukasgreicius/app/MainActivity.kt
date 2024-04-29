package com.lukasgreicius.app

import NoteViewModel
import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesList(viewModel,
                { navigateToAddNoteActivity() },
                { navigateToDeleteNoteActivity() })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.readData();
    }

    private fun navigateToAddNoteActivity() {
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDeleteNoteActivity() {
        val intent = Intent(this, DeleteNoteActivity::class.java)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesList(
    viewModel: NoteViewModel,
    onAddNoteClicked: () -> Unit,
    onDeleteNoteClicked: () -> Unit,
) {
    val notes by viewModel.notesLiveData.observeAsState(listOf())

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(onClick = { onAddNoteClicked() }) {
                Text("Add Note")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onDeleteNoteClicked() }) {
                Text("Delete Note")
            }
        }
    }) {
        LazyColumn {
            items(notes) { note ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Title: ${note.title}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Content: ${note.content}")
                }
            }
        }
    }
}
