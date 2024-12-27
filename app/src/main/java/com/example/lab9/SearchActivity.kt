package com.example.lab9


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.api.Client
import com.example.lab9.api.OMDbMovie


import kotlinx.coroutines.launch
import kotlin.math.log

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private val apiKey = "39e646c9" // Замените на ваш ключ OMDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.recyclerViewSearch)
        emptyState = findViewById(R.id.empty_state_search)

        recyclerView.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }

        val query = intent.getStringExtra("query") ?: ""
        performSearch(query)
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch {
            try {
                val response = Client.apiService.searchMovies(apiKey, query)
                if (response.isSuccessful && response.body()?.response == "True") {
                    val movies = response.body()?.search ?: emptyList()
                    if (movies.isEmpty()) {
                        showEmptyState()
                    } else {
                        setupRecyclerView(movies)
                    }
                } else {
                    showEmptyState()
                    Toast.makeText(this@SearchActivity, response.body()?.error ?: "Нет результатов", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                showEmptyState()
                Toast.makeText(this@SearchActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                e.message?.let { Log.i("Search", it) }
            }

        }
    }

    private fun setupRecyclerView(movies: List<OMDbMovie>) {
        val adapter = SearchMovieAdapter(movies) { omdbMovie ->
            val intent = Intent(this, AddActivity::class.java).apply {
                putExtra("imdbID", omdbMovie.imdbID)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun showEmptyState() {
        recyclerView.visibility = View.GONE
        emptyState.visibility = View.VISIBLE
    }
}
