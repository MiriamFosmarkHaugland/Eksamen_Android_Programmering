package no.kristiania.pgr208_android_exam.navigation

import kotlinx.serialization.Serializable

@Serializable
object DefaultCharacterList

@Serializable
data class DefaultCharacterDetails(
    val characterId: Int
)

@Serializable
object CustomCharacterList

@Serializable
object CreateCharacter

@Serializable
data class CustomCharacterEdit(
    val characterId: Int
)
