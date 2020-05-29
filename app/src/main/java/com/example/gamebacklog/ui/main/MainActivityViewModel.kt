package com.example.gamebacklog.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gamebacklog.database.GameRepository
import com.example.gamebacklog.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) :
    AndroidViewModel(application){

    private val gameRepository = GameRepository(application.applicationContext)

    private val ioScope = CoroutineScope(Dispatchers.IO)

    val games = gameRepository.getAllGames()

    fun deleteGame(game: Game)  {
        ioScope.launch {
            gameRepository.deleteGame(game)
        }
    }

    fun insertGame(game: Game)  {
        ioScope.launch {
            gameRepository.insertGame(game)
        }
    }

    fun deleteAllGames()    {
        ioScope.launch {
            gameRepository.deleteAllGames()
        }
    }

}