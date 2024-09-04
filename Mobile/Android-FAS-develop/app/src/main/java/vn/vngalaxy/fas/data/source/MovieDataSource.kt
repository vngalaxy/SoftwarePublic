package vn.vngalaxy.fas.data.source

import vn.vngalaxy.fas.model.MoviesResponse

interface MovieDataSource {

    interface Local

    interface Remote {
        suspend fun getPopularMovies(page: Int): MoviesResponse
    }
}