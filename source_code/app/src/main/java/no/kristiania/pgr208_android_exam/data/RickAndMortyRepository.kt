package no.kristiania.pgr208_android_exam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import no.kristiania.pgr208_android_exam.data.room.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// From Canvas : pgr208-10-lecture-code-finish
object RickAndMortyRepository {
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    // From Canvas : pgr208-10-lecture-code-finish
    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // From Canvas : pgr208-10-lecture-code-finish
    private val _rickAndMortyService = _retrofit.create(RickAndMortyService::class.java)

    // From Canvas : pgr208-10-lecture-code-finish
    private lateinit var _appDatabase: AppDatabase

    // From Canvas : pgr208-10-lecture-code-finish
    private val _defaultCharacterDao by lazy { _appDatabase.defaultCharacterDao() }
    private val _customCharacterDao by lazy { _appDatabase.customCharacterDao() }

    // From Canvas : pgr208-10-lecture-code-finish
    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "RickAndMorty-database"
        ).build()
    }


    // This function fetches all characters from the API and stores them in the local database
    suspend fun getDefaultCharacters(): List<Character> {

        val allCharacters = mutableListOf<Character>()

        try {
            val pageResponse = _rickAndMortyService.getCharacters(1)
            if (pageResponse.isSuccessful) {
                val pageBody = pageResponse.body()
                if (pageBody != null) {

                    // Add all characters from the first page to our list
                    allCharacters.addAll(pageBody.results)

                    // Get the total number of pages available from the API
                    val totalPages = pageBody.info.pages

                    // Loop through all remaining pages (starting from page 2)
                    for (pageNumber in 2..totalPages) {
                        // Fetch each following page
                        val nextPage = _rickAndMortyService.getCharacters(page = pageNumber)
                        if (nextPage.isSuccessful) {
                            // Get the characters from this page and add them to our list
                            val nextPageBody = nextPage.body()
                            if (nextPageBody != null) {
                                allCharacters.addAll(nextPageBody.results)
                            }
                        }
                    }
                    // Save all fetched characters to the local database
                    _defaultCharacterDao.insertList(allCharacters)
                }
                return _defaultCharacterDao.getAll()
            } else {
                throw Exception("Response from API was unsuccessful")
            }
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to fetch default characters: ${e.message}"
            )
            // Return whatever characters we have stored in the local database.
            return _defaultCharacterDao.getAll()
        }
    }

    fun getDefaultAliveCharacters(): List<Character> {
        try {
            _defaultCharacterDao.getAllAliveCharacters()
            return _defaultCharacterDao.getAllAliveCharacters()
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to fetch alive characters: ${e.message}"
            )
            return _defaultCharacterDao.getAllAliveCharacters()
        }
    }


    fun getDefaultDeadCharacters(): List<Character> {
        try {
            _defaultCharacterDao.getAllDeadCharacters()
            return _defaultCharacterDao.getAllDeadCharacters()
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to fetch dead characters: ${e.message}"
            )
            return _defaultCharacterDao.getAllDeadCharacters()
        }
    }

    fun getDefaultCharacterById(id: Int): Character {
        try {
            _defaultCharacterDao.getCharacterById(id)
            return _defaultCharacterDao.getCharacterById(id)
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to fetch default character with id: ${id}: ${e.message}"
            )
            return _defaultCharacterDao.getCharacterById(id)
        }
    }

    fun getCustomCharacters(): List<Character> {
        try {
            _customCharacterDao.getAll()
            return _customCharacterDao.getAll()
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to fetch custom characters: ${e.message}")
            return _customCharacterDao.getAll()
        }
    }

    fun getCustomCharacterById(id: Int): Character {
        try {
            _customCharacterDao.getCharacterById(id)
            return _customCharacterDao.getCharacterById(id)
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to fetch custom character with id: ${id}: ${e.message}"
            )
            return _customCharacterDao.getCharacterById(id)
        }
    }

    fun addCustomCharacter(character: Character) {
        try {
            _customCharacterDao.insertCustomCharacter(character)
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to create custom character: ${e.message}")
        }
    }

    fun updateCustomCharacter(character: Character) {
        try {
            _customCharacterDao.updateCharacter(character)
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to update custom character: ${e.message}")
        }
    }

    fun deleteCustomCharacter(character: Character) {
        try {
            _customCharacterDao.deleteCharacter(character)
        } catch (e: Exception) {
            Log.e(
                "RickAndMortyRepository",
                "Failed to delete custom character: ${e.message}")
            throw e
        }
    }
}