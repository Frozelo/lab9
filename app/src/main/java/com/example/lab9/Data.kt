package com.example.lab9


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val genre: String? = null
)
