package com.example.lab9


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var fabAdd: FloatingActionButton
    private var adapter: MovieAdapter? = null
    private val selectedIds = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        emptyState = findViewById(R.id.empty_state)
        fabAdd = findViewById(R.id.fab_add)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        fabAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteSelectedMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter(emptyList()) { id, isChecked ->
            if (isChecked) {
                selectedIds.add(id)
            } else {
                selectedIds.remove(id)
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
    }

    private fun observeData() {
        movieViewModel.allMovies.observe(this) { movies ->
            if (movies.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
            } else {
                emptyState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter?.updateMovies(movies)
            }
        }
    }

    private fun deleteSelectedMovies() {
        if (selectedIds.isNotEmpty()) {
            movieViewModel.deleteSelected(selectedIds.toList())
            Toast.makeText(this, "Фильмы удалены", Toast.LENGTH_SHORT).show()
            selectedIds.clear()
        } else {
            Toast.makeText(this, "Выберите фильмы для удаления", Toast.LENGTH_SHORT).show()
        }
    }
}
