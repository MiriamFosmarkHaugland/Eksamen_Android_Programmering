package no.kristiania.pgr208_android_exam.screens.custom_character_edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.pgr208_android_exam.data.Character
import no.kristiania.pgr208_android_exam.data.RickAndMortyRepository

class CustomCharacterEditViewModel : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character = _character.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _image = MutableStateFlow("")
    val image = _image.asStateFlow()

    private val _species = MutableStateFlow("")
    val species = _species.asStateFlow()

    fun setName(input: String) {
        _name.value = input
    }

    fun setImage(input: String) {
        _image.value = input
    }

    fun setSpecies(input: String) {
        _species.value = input
    }

    private fun clearInputs() {
        _name.value = ""
        _image.value = ""
        _species.value = ""
    }

    fun loadCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val character = RickAndMortyRepository.getCustomCharacterById(id)
                _character.value = character
            } catch (e: Exception) {
                Log.e(
                    "CustomCharacterEditViewModel",
                    "Failed to load custom character with ${id}: ${e.message}")
            }
        }
    }

    fun updateCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val copiedCharacter = _character.value
                if (copiedCharacter != null) {
                    if (_name.value.isNotBlank()) {
                        copiedCharacter.name = _name.value
                    }
                    if(_image.value.isNotBlank()) {
                        copiedCharacter.image = _image.value
                    }
                    if(_species.value.isNotBlank()) {
                        copiedCharacter.species = _species.value
                    }
                    RickAndMortyRepository.updateCustomCharacter(copiedCharacter)
                    clearInputs()
                }
            } catch (e: Exception) {
                Log.e(
                    "CustomCharacterEditViewModel",
                    "Failed to update the custom character: ${e.message}")
            }
        }
    }

    fun deleteCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val copiedCharacter = _character.value
                if (copiedCharacter != null) {
                    RickAndMortyRepository.deleteCustomCharacter(copiedCharacter)
                }
            } catch (e: Exception) {
                Log.e(
                    "CustomCharacterEditViewModel",
                    "Failed to delete custom character: ${e.message}")
            }
        }
    }
}