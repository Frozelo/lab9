package com.example.lab9


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab9.api.OMDbMovie


class SearchMovieAdapter(
    private val movies: List<OMDbMovie>,
    private val onItemClick: (OMDbMovie) -> Unit
) : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>() {

    inner class SearchMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.ivPosterSearch)
        val title: TextView = itemView.findViewById(R.id.tvTitleSearch)
        val year: TextView = itemView.findViewById(R.id.tvYearSearch)
        val genre: TextView = itemView.findViewById(R.id.tvGenreSearch) // Если отображаете жанр
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_search, parent, false)
        return SearchMovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.year.text = movie.year

        Glide.with(holder.poster.context)
            .load(movie.poster)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }
}
