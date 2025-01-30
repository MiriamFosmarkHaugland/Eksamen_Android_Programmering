package no.kristiania.pgr208_android_exam.screens.custom_character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.pgr208_android_exam.data.Character
import no.kristiania.pgr208_android_exam.data.RickAndMortyRepository

class CustomCharacterListViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    fun loadCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val characters = RickAndMortyRepository.getCustomCharacters()
                _characters.value = characters
            } catch (e: Exception) {
                Log.e(
                    "CustomCharacterListViewModel",
                    "Failed to load custom characters: ${e.message}")
                // "finally" ensures that the loading state is set back to false regardless of success or failure when we loadCustomCharacters.
            } finally {
                _isLoading.value = false
            }
        }
    }
}