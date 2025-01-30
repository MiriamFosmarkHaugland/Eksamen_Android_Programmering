package no.kristiania.pgr208_android_exam.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// autoGenerate property: https://stackoverflow.com/questions/44109700/how-to-make-primary-key-as-autoincrement-for-room-persistence-lib
@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var image: String,
    var species: String,
    var status: String,
    val type: String?,
    val gender: String,
    @ColumnInfo(defaultValue = "0")
    val custom: Boolean
)

data class CharacterListResponse(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)