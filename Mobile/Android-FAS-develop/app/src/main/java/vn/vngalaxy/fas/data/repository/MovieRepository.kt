package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.model.MoviesResponse
import vn.vngalaxy.fas.shared.scheduler.DataResult

interface MovieRepository {
    /**
     * Remote
     */

    suspend fun getPopularMovies(page: Int): DataResult<MoviesResponse>

}