package no.kristiania.pgr208_android_exam.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import no.kristiania.pgr208_android_exam.data.Character

@Dao
interface CustomCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomCharacter(character: Character)

    @Query("SELECT * FROM Character WHERE custom = 1")
    fun getAll(): List<Character>

    //https://stackoverflow.com/questions/50965358/room-retrieving-the-user-info-by-using-id
    @Query("SELECT * FROM Character WHERE id= :id AND custom = 1")
    fun getCharacterById(id: Int) : Character

    // https://developer.android.com/training/data-storage/room/accessing-data
    @Update
    fun updateCharacter(vararg character: Character)

    @Delete
    fun deleteCharacter(vararg character: Character)
}