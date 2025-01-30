package no.kristiania.pgr208_android_exam.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import no.kristiania.pgr208_android_exam.data.Character

@Database(
    entities = [Character::class],
    version = 7,
    exportSchema = true,
    autoMigrations = [
        AutoMigration ( from = 6, to = 7 )
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun defaultCharacterDao(): DefaultCharacterDao
    abstract fun customCharacterDao(): CustomCharacterDao
}