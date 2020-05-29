package com.example.gamebacklog.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game
import kotlinx.android.synthetic.main.card_game.view.*
import java.text.SimpleDateFormat
import java.util.*

class GameAdapter(private val games:List<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_game, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(game: Game)    {
            itemView.tvTitle.text = game.title
            itemView.tvPlatform.text = game.platform
            itemView.tvRelease.text = getDateFormatted(game.release)
        }
    }

    // https://stackoverflow.com/questions/13579632/android-date-year-month-day
    private fun getDateFormatted(date : Date) : String  {
        return "Release: " + SimpleDateFormat("d MMM yyyy").format(date)
    }


}