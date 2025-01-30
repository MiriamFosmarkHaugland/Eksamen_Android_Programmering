package no.kristiania.pgr208_android_exam.screens.default_character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.kristiania.pgr208_android_exam.data.Character
import no.kristiania.pgr208_android_exam.data.RickAndMortyRepository

class DefaultCharacterListViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    private val _isAliveFilterSelected = MutableStateFlow(false)
    val isAliveFilterSelected = _isAliveFilterSelected.asStateFlow()

    private val _isDeadFilterSelected = MutableStateFlow(false)
    val isDeadFilterSelected = _isDeadFilterSelected.asStateFlow()

    init {
        loadCharacters()
    }

    fun toggleIsDeadFilter() {
       _isDeadFilterSelected.value = !_isDeadFilterSelected.value
    }

    fun toggleIsAliveFilter() {
        _isAliveFilterSelected.value = !_isAliveFilterSelected.value
    }

    fun loadCharacters() {
        viewModelScope.launch(Dispatchers.IO) {

            val characters = mutableListOf<Character>()

            if (_isAliveFilterSelected.value) {
                try {
                    _isLoading.value = true
                    characters.addAll(RickAndMortyRepository.getDefaultAliveCharacters())
                } catch (e: Exception) {
                    Log.e(
                        "DefaultCharacterListViewModel",
                        "Failed to load default alive characters: ${e.message}"
                    )
                }
            }

            if (_isDeadFilterSelected.value) {
                try {
                    _isLoading.value = true
                    characters.addAll(RickAndMortyRepository.getDefaultDeadCharacters())
                } catch (e: Exception) {
                    Log.e(
                        "DefaultCharacterListViewModel",
                        "Failed to load default dead characters: ${e.message}"
                    )
                }
            }

            if (!_isAliveFilterSelected.value && !_isDeadFilterSelected.value) {
                try {
                    _isLoading.value = true
                    characters.addAll(RickAndMortyRepository.getDefaultCharacters())
                } catch (e: Exception) {
                    Log.e(
                        "DefaultCharacterListViewModel",
                        "Failed to load all default characters: ${e.message}"
                    )
                }
            }
            _characters.value = characters
            _isLoading.value = false
        }
    }
}