package com.example.lab9


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab9.database.MovieDatabase
import com.example.lab9.database.Repository
import kotlinx.coroutines.launch

// MovieViewModel.kt
class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val allMovies: LiveData<List<Movie>>

    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = Repository(movieDao)
        allMovies = repository.allMovies
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun deleteSelected(ids: List<String>) = viewModelScope.launch {
        repository.delete(ids)
    }
}
