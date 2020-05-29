package com.example.gamebacklog.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game
import com.example.gamebacklog.ui.edit.AddActivity
import com.example.gamebacklog.ui.edit.EXTRA_GAME

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val GAME_REQUEST_CODE = 100
class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val games = arrayListOf<Game>()
    private val undoGamesList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()

    }

    private fun initViews() {
        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, GAME_REQUEST_CODE)
        }

        rvCards.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvCards.adapter = gameAdapter
        createItemTouchHelper().attachToRecyclerView(rvCards)
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.games.observe(this, Observer  { games ->
            this@MainActivity.games.clear()
            this@MainActivity.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)   {
            when (requestCode)  {
                GAME_REQUEST_CODE ->    {
                    val game = data!!.getParcelableExtra<Game>(EXTRA_GAME)
                    mainActivityViewModel.insertGame(game)
                }
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper    {

        val callback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT)  {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =  viewHolder.adapterPosition
                val swipedGame = games[position]
                mainActivityViewModel.deleteGame(swipedGame)

                //Create undo button snackbar
                val snackbar = Snackbar.make(findViewById(android.R.id.content),"Game deleted",Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.undo)   {
                    mainActivityViewModel.insertGame(swipedGame)
                }
                snackbar.show()
            }
        }
        return ItemTouchHelper(callback)
    }
}
