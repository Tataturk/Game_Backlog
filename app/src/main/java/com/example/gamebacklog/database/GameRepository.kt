package com.example.gamebacklog.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gamebacklog.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()

    }

    suspend fun insertGame(game: Game)  {
        return gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game)  {
        return gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames()    {
        return gameDao.deleteAllGames()
    }

}