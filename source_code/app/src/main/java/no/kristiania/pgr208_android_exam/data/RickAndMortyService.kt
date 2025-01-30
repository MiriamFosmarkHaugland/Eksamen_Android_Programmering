package no.kristiania.pgr208_android_exam.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterListResponse>
}