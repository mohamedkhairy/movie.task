package khairy.com.movietask.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import arrow.core.left
import khairy.com.movietask.extensions.Errors
import khairy.com.movietask.extensions.Result
import khairy.com.movietask.extensions.callAPI
import khairy.com.movietask.extensions.isNotNull
import khairy.com.movietask.model.MoviesResult
import khairy.com.movietask.model.SearchData
import khairy.com.movietask.webAccess.WebAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieSearchViewModel(app: Application) : AndroidViewModel(app){

    private val jobs: MutableList<Job> = mutableListOf()

    private val liveResult: MutableLiveData<Either<Result.Error, List<MoviesResult>>> = MutableLiveData()

    fun getmovieList(): LiveData<Either<Result.Error, List<MoviesResult>>> {
        return liveResult
    }

    fun getSearchData() {
        jobs += GlobalScope.launch(Dispatchers.IO) {
            val r: Either<Result.Error, Result.Success<SearchData>> =
                callAPI { WebAccess.movieApi.getPartsAsync() }
            ListResult(r)
        }
    }

    private fun ListResult(result: Either<Result.Error, Result.Success<SearchData>>) {
        when (result) {
            is Either.Right -> {
                if (result.b.t.isNotNull()) rightSide(result.b)
                else leftSide(Result.Error(Errors.UnknownError))
            }
            is Either.Left  -> leftSide(result.a)
        }
    }

    private fun rightSide(r: Result.Success<SearchData>) {
        liveResult.postValue(Either.right(r.t.results))
    }

    private fun leftSide(l: Result.Error) {
        liveResult.postValue(l.left())
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { if (it.isActive) it.cancel() }
    }
}