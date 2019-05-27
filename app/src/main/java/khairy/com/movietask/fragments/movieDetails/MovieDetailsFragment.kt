package khairy.com.movietask.fragments.movieDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import khairy.com.movietask.R
import khairy.com.movietask.model.MoviesResult
import kotlinx.android.synthetic.main.movie_details.*
import kotlinx.android.synthetic.main.movie_details.view.*

class MovieDetailsFragment : Fragment() {

    lateinit var moviesResult:MoviesResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.movie_details, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        moviesResult = this.arguments?.getParcelable("mResult" ) as MoviesResult
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prepareView(view)
    }

    private fun prepareView(view: View){
        val coverUrl = "http://image.tmdb.org/t/p/w185/"+moviesResult.poster_path

        title.text = moviesResult.title
        date.text = moviesResult.release_date
        rate.text = moviesResult.vote_average
        review.text = moviesResult.overview
        Glide.with(view)
            .load(coverUrl)
            .into(view.layer)
    }

    companion object {

        const val TAG = "MovieDetailsFragment"

        @JvmStatic
        fun newInstance(mResult: MoviesResult) = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("mResult",mResult)
            }
        }
    }
}