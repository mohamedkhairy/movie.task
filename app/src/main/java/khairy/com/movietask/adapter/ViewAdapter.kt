package khairy.com.movietask.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import khairy.com.movietask.R
import khairy.com.movietask.model.MoviesResult
import kotlinx.android.synthetic.main.movie_list_item.view.*

class ViewAdapter (var movieItemList: List<MoviesResult>, val clickListener: (MoviesResult) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // LayoutInflater: takes ID from layout defined in XML.
        // Instantiates the layout XML into corresponding View objects.
        // Use context from main app -> also supplies theme layout values!
        val inflater = LayoutInflater.from(parent.context)
        // Inflate XML. Last parameter: don't immediately attach new view to the parent view group
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as CardViewHolder).bind(movieItemList[position], clickListener)
    }

    override fun getItemCount() = movieItemList.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mr: MoviesResult, clickListener: (MoviesResult) -> Unit) {
            val coverUrl = "http://image.tmdb.org/t/p/w185/"+mr.poster_path
            Log.d("xxx" , coverUrl)
            itemView.movie_title.text = mr.title
            itemView.release.text = mr.release_date
            Glide.with(itemView)
                .load(coverUrl)
                .into(itemView.cover)
            itemView.setOnClickListener { clickListener(mr)}
        }
    }
}