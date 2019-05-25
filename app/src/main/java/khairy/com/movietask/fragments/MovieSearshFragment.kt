package khairy.com.movietask.fragments
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import arrow.core.Either
import khairy.com.movietask.R
import khairy.com.movietask.adapter.ViewAdapter
import khairy.com.movietask.extensions.Result
import khairy.com.movietask.model.MoviesResult
import kotlinx.android.synthetic.main.search_fragment.*

class MovieSearshFragment : Fragment(){


    private lateinit var adapter: ViewAdapter

    val movieSearchViewModel: MovieSearchViewModel by lazy {
        ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView()
        observeMovieResult()
        movieSearchViewModel.getSearchData()
    }

    private fun prepareView(){

        recyclerView.layoutManager = GridLayoutManager(activity ,2)
        recyclerView.hasFixedSize()
        adapter = ViewAdapter(listOf(), { movieItem : MoviesResult -> moviewItemClicked(movieItem) })
        recyclerView.adapter = adapter

    }

    private fun observeMovieResult() {
        movieSearchViewModel.getmovieList()
            .observe(this, androidx.lifecycle.Observer { rb: Either<Result.Error, List<MoviesResult>> ->

                rb.fold({
                    Log.d("xxx", it.error.toString())
                },{
                    Log.d("xxx", "done")
                    Log.d("xxx", it[0].title)

                    adapter.movieItemList = it ?: listOf()
                    adapter.notifyDataSetChanged()
                })

            })
    }

    private fun moviewItemClicked(item: MoviesResult) {

        Toast.makeText(activity, "Clicked: ${item.title}", Toast.LENGTH_LONG).show()

    }

    companion object {

        const val TAG = "MovieSearchFragment"
    }

}