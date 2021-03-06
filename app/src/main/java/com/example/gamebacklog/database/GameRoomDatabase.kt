package com.example.gamebacklog.database

import android.content.Context
import androidx.room.*
import com.example.gamebacklog.model.Game

@TypeConverters(Converters::class)
@Database(entities = [Game::class], version = 1, exportSchema = false)

abstract class GameRoomDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object    {
        private const val DATABASE_NAME = "GAME_DATABASE"

        @Volatile
        private var INSTANCE: GameRoomDatabase? = null

        fun getDatabase(context: Context) : GameRoomDatabase?   {
            if (INSTANCE == null)   {
                synchronized(GameRoomDatabase::class.java)   {
                    if (INSTANCE == null)   {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}