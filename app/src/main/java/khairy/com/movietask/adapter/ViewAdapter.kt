package khairy.com.movietask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import khairy.com.movietask.R
import khairy.com.movietask.model.MoviesResult
import kotlinx.android.synthetic.main.movie_list_item.view.*

class ViewAdapter(var movieItemList: MutableList<MoviesResult>, val clickListener: (MoviesResult) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as CardViewHolder).bind(movieItemList[position], clickListener)
    }

    override fun getItemCount() = movieItemList.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mr: MoviesResult, clickListener: (MoviesResult) -> Unit) {
            val coverUrl = "http://image.tmdb.org/t/p/w185/"+mr.poster_path
            itemView.movie_title.text = mr.title
            itemView.release.text = mr.release_date
            Glide.with(itemView)
                .load(coverUrl)
                .into(itemView.cover)
            itemView.setOnClickListener { clickListener(mr)}
        }
    }
}