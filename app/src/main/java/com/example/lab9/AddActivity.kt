package com.example.lab9


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.api.Client
import com.example.lab9.api.OMDbMovie

import kotlinx.coroutines.launch

// AddActivity.kt
class AddActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var etYear: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private val apiKey = "39e646c9"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        etSearch = findViewById(R.id.et_search)
        etYear = findViewById(R.id.et_year)
        btnSearch = findViewById(R.id.btn_search)
        recyclerView = findViewById(R.id.recyclerViewAdd)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            val year = etYear.text.toString().trim()
            if (query.isNotEmpty()) {
                searchMovies(query, if (year.isNotEmpty()) year else null)
            } else {
                Toast.makeText(this, "Введите название фильма", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
    }

    private fun searchMovies(query: String, year: String?) {
        lifecycleScope.launch {
            try {
                println(query)
                val response = Client.apiService.searchMovies(apiKey, query, year)
                if (response.isSuccessful && response.body()?.response == "True") {
                    val movies = response.body()?.search ?: emptyList()
                    setupRecyclerView(movies)
                } else {
                    println(response)
                    Toast.makeText(this@AddActivity, response.body()?.error ?: "Ошибка поиска", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(movies: List<OMDbMovie>) {
        val adapter = AddMovieAdapter(movies) { omdbMovie ->

            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra("imdbID", omdbMovie.imdbID)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
