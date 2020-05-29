package com.example.gamebacklog.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "games")
data class Game (
    var title: String,
    var platform: String,
    var release: Date,
    @PrimaryKey var id: Long? = null
) : Parcelable