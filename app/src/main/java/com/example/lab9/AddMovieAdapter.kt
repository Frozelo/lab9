package com.example.lab9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab9.api.OMDbMovie

class AddMovieAdapter(
    private val movies: List<OMDbMovie>,
    private val onItemClick: (OMDbMovie) -> Unit
) : RecyclerView.Adapter<AddMovieAdapter.AddMovieViewHolder>() {

    inner class AddMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.ivPosterAdd)
        val title: TextView = itemView.findViewById(R.id.tvTitleAdd)
        val year: TextView = itemView.findViewById(R.id.tvYearAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_add, parent, false)
        return AddMovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: AddMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.year.text = movie.year
        Glide.with(holder.poster.context)
            .load(movie.poster)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }
}