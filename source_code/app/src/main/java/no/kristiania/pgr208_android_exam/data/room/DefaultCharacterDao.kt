package no.kristiania.pgr208_android_exam.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.pgr208_android_exam.data.Character

@Dao
interface DefaultCharacterDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertList(characters: List<Character>)

   @Query("SELECT * FROM Character WHERE custom = 0")
   fun getAll(): List<Character>

   //https://stackoverflow.com/questions/50965358/room-retrieving-the-user-info-by-using-id
   @Query("SELECT * FROM Character WHERE id= :id AND custom = 0")
   fun getCharacterById(id: Int) : Character

   @Query("SELECT * FROM Character WHERE status = 'Alive'")
   fun getAllAliveCharacters(): List<Character>

   @Query("SELECT * FROM Character WHERE status = 'Dead'")
   fun getAllDeadCharacters(): List<Character>
}