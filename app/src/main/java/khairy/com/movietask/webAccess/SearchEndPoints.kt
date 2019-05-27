package khairy.com.movietask.webAccess

import khairy.com.movietask.model.SearchData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchEndPoints {
    @GET("search/movie?api_key=b3070a5d3abfb7c241d2688d066914e7")
    fun getPartsAsync(@Query("query") query: String ,
                      @Query("page") page: Int): Deferred<SearchData>

}