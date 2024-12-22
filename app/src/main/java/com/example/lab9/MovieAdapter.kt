import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab9.Movie
import com.example.lab9.R

class MovieAdapter(
    private val movies: List<Movie>,
    private val onItemChecked: (String, Boolean) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val selectedIds = mutableSetOf<String>()

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.ivPoster)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val year: TextView = itemView.findViewById(R.id.tvYear)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_main, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.year.text = movie.year
        Glide.with(holder.poster.context)
            .load(movie.poster)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.poster)

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = selectedIds.contains(movie.imdbID)
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedIds.add(movie.imdbID)
            } else {
                selectedIds.remove(movie.imdbID)
            }
            onItemChecked(movie.imdbID, isChecked)
        }
    }

    fun getSelectedIds(): List<String> = selectedIds.toList()
}
