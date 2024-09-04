package vn.vngalaxy.fas.data.repository

import android.util.Log
import vn.vngalaxy.fas.data.source.MovieDataSource
import vn.vngalaxy.fas.model.MoviesResponse
import vn.vngalaxy.fas.shared.scheduler.DataResult


class MovieRepositoryImpl(
    private val moviesDataSource: MovieDataSource.Remote,
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): DataResult<MoviesResponse> {
        return try {
            val response = moviesDataSource.getPopularMovies(page)
            DataResult.Success(response)
        } catch (e: Exception) {
            Log.d("Dataaaaa", e.toString())
            DataResult.Error(e)
        }
    }

}