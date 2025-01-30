package no.kristiania.pgr208_android_exam.screens.default_character_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.pgr208_android_exam.data.Character
import no.kristiania.pgr208_android_exam.data.RickAndMortyRepository

class DefaultCharacterDetailsViewModel : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character = _character.asStateFlow()

    fun loadCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val character = RickAndMortyRepository.getDefaultCharacterById(id)
                _character.value = character
            } catch (e: Exception) {
                Log.e(
                    "CustomCharacterEditViewModel",
                    "Failed to load default character with ${id}: ${e.message}")
            }
        }
    }
}