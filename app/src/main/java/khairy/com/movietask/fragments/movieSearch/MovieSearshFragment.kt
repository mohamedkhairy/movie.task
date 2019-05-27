package khairy.com.movietask.fragments.movieSearch
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Either
import khairy.com.movietask.R
import khairy.com.movietask.adapter.ViewAdapter
import khairy.com.movietask.extensions.Result
import khairy.com.movietask.extensions.isNotNull
import khairy.com.movietask.fragments.movieDetails.MovieDetailsFragment
import khairy.com.movietask.model.MoviesResult
import khairy.com.movietask.model.SearchData
import kotlinx.android.synthetic.main.search_fragment.*

class MovieSearshFragment : Fragment(){


    private lateinit var adapter: ViewAdapter
    private var movieName: String? = "n/a"
    private var totalPage: Int = 0
    private var currentPage: Int = 1
    private var isloading = false



    private val movieSearchViewModel: MovieSearchViewModel by lazy {
        ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
         movieName = this.arguments?.getString("movieName" , "n/a")


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareView()
        observeLoading()
        observeMovieResult()
        movieSearchViewModel.getSearchData(movieName!! , currentPage)
    }


    @SuppressLint("WrongConstant")
    private fun prepareView(){

        val recyclerManeger = GridLayoutManager(activity ,2)
        recyclerView.layoutManager = recyclerManeger
        recyclerView.hasFixedSize()
        adapter = ViewAdapter(mutableListOf()) { movieItem : MoviesResult -> moviewItemClicked(movieItem) }
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                val visibleItemCount = recyclerManeger.childCount
                val totalItemCount = recyclerManeger.itemCount
                val firstVisibleItemPosition = recyclerManeger.findFirstVisibleItemPosition()

                if ( totalPage > 0 && currentPage <= totalPage && !isloading ) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        isloading = true
                        currentPage += 1
                        movieSearchViewModel.getSearchData(movieName!! , currentPage)
                    }
                }
            }
        })

    }

    private fun observeMovieResult() {
        movieSearchViewModel.getmovieList()
            .observe(this, Observer { rb: Either<Result.Error, SearchData> ->

                rb.fold(
                    { Log.d("xxx", "Error")},
                    {
                        isloading = false

                        if (rb.isNotNull()){

                            currentPage = it.page
                            totalPage = it.total_pages
                            adapter.movieItemList.addAll(it.results)
                            adapter.notifyDataSetChanged()
                        }
                    })


            })
    }

    private fun observeLoading() {
        movieSearchViewModel.getLoading().observe(this, Observer { optionLoading: Boolean? ->
            optionLoading?.let { loading ->
                when (loading) {
                    true -> {
                     subloading.visibility = View.VISIBLE
                    }
                    else -> {
                        subloading.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun moviewItemClicked(item: MoviesResult) {

        val movieDetailsFragment = MovieDetailsFragment.newInstance(item)
        childFragmentManager.beginTransaction().add(R.id.movie_search_linear, movieDetailsFragment , MovieDetailsFragment.TAG).addToBackStack(MovieDetailsFragment.TAG).commitAllowingStateLoss()
    }

    companion object {

        const val TAG = "MovieSearchFragment"

        @JvmStatic
        fun newInstance(movieName: String) = MovieSearshFragment().apply {
            arguments = Bundle().apply {
                putString("movieName",movieName)
            }
        }
    }

}