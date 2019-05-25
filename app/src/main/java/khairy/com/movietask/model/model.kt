package khairy.com.movietask.model
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchData(
    val page: Int,
    val results: List<MoviesResult>,
    val total_pages: Int,
    val total_results: Int
)

@JsonClass(generateAdapter = true)
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
)