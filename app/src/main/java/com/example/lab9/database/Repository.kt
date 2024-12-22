package com.example.lab9.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.example.lab9.Movie

class Repository (private val dao: MovieDao) {
    val allMovies: LiveData<List<Movie>> = dao.getAllMovies()

    suspend fun insert(movie: Movie) {
        dao.insert(movie)
    }

    suspend fun delete(ids: List<String>) {
        dao.deleteMoviesByIds(ids)
    }
}