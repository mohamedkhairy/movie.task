package khairy.com.movietask.model
import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class SearchData(
    val page: Int,
    val results: List<MoviesResult>,
    val total_pages: Int,
    val total_results: Int
) :Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class MoviesResult(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: String?,
    val vote_count: Int?
) :Parcelable