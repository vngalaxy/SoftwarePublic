package vn.vngalaxy.fas.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie", primaryKeys = ["id"])
@Parcelize
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val name: String? = "",
    @SerializedName("vote_count") val voteCount: Int? = 0,
    @SerializedName("original_title") val title: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
) : Parcelable

@Parcelize
data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("cast")
    val cast: List<Movie>,
) : Parcelable