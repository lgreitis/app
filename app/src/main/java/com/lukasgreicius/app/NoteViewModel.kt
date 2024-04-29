import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val context: Context = application.applicationContext

    private var notes = MutableLiveData<List<Note>>(listOf())
    val notesLiveData: LiveData<List<Note>>
        get() = notes

    init {
        readData()
    }

    suspend fun addNote(note: Note) {
        val currentList = notes.value ?: listOf()
        notes.value = currentList + note
        writeToFile()
    }

    suspend fun deleteNoteByTitle(title: String) {
        notes.value = notes.value?.filter { it.title != title }
        writeToFile()
    }

    private suspend fun writeToFile() {
        val sharedPreferences = context.getSharedPreferences("todoApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(notes.value)
        editor.putString("notes_list", json)
        editor.apply()
    }

    fun readData() {
        val sharedPreferences = context.getSharedPreferences("todoApp", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("notes_list", null)
        val type = object : TypeToken<List<Note>>() {}.type

        notes.value = gson.fromJson(json, type) ?: emptyList()
    }
}