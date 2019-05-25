package khairy.com.movietask

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Either
import khairy.com.movietask.extensions.Result
import khairy.com.movietask.fragments.MovieSearchViewModel
import khairy.com.movietask.fragments.MovieSearshFragment
import khairy.com.movietask.model.MoviesResult
import khairy.com.movietask.model.SearchData
import khairy.com.movietask.webAccess.WebAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

//    val movieSearchViewModel: MovieSearchViewModel by lazy {
//        ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
        .replace(R.id.main_view, MovieSearshFragment(), MovieSearshFragment.TAG)
        .commit()
//        observeMovieResult()
//        movieSearchViewModel.getSearchData()

//        GlobalScope.launch(Dispatchers.Main) {
//
//            val webResponse = WebAccess.movieApi.getPartsAsync().await()
//            if (webResponse.isSuccessful) {
//
//                val partList: SearchData? = webResponse.body()
//                Log.d("xxx", partList?.total_pages.toString())
//
//
//            } else {
//                // Print error information to the console
//                Log.e("xxxx", "Error ${webResponse.code()}")
//                Toast.makeText(this@MainActivity, "Error ${webResponse.code()}", Toast.LENGTH_LONG).show()
//            }
//        }
    }

//    private fun observeMovieResult() {
//        movieSearchViewModel.getmovieList()
//            .observe(this, androidx.lifecycle.Observer { rb: Either<Result.Error, List<MoviesResult>> ->
//
//                rb.fold({
//                    Log.d("xxx", it.error.toString())
//                },{
//                    Log.d("xxx", "done")
//
//                    Log.d("xxx", it[0].title)
//                })
//
//        })
//    }

}
