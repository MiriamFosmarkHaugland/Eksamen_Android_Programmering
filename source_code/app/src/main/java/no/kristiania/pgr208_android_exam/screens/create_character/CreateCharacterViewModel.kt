package no.kristiania.pgr208_android_exam.screens.create_character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.pgr208_android_exam.data.Character
import no.kristiania.pgr208_android_exam.data.RickAndMortyRepository

class CreateCharacterViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _image = MutableStateFlow("")
    val image = _image.asStateFlow()

    private val _species = MutableStateFlow("")
    val species = _species.asStateFlow()

    private val _status = MutableStateFlow("")

    private val _isInputEmpty = MutableStateFlow(false)

    fun setName(input: String) {
        _name.value = input
        _isInputEmpty.value = _name.value.isBlank()
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

    fun addCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var newSpecies = _species.value
                if (newSpecies.isBlank()) {
                    newSpecies = "unknown"
                }

                var newImage = _image.value
                if (newImage.isBlank()) {
                    newImage = "https://rickandmortyapi.com/api/character/avatar/19.jpeg"
                }

                var newStatus = _status.value
                if (newStatus.isBlank()) {
                    newStatus = "Unknown"
                }

                val character = Character(
                    name = _name.value,
                    image = newImage,
                    species = newSpecies,
                    status =  newStatus,
                    custom = true,
                    type = "null",
                    gender = "null"
                )
                RickAndMortyRepository.addCustomCharacter(character)
                clearInputs()
            } catch (e: Exception) {
                Log.e(
                    "CreateCharacterViewModel",
                    "Failed to create custom character: ${e.message}")
            }
        }
    }
}