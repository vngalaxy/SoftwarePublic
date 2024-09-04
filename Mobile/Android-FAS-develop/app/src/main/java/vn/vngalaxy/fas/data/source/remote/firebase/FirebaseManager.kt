package vn.vngalaxy.fas.data.source.remote.firebase

class FirebaseManager {
    companion object {
        private const val COLLECTION_MOVIES = "movies"
    }

//    suspend fun getMovie(): List<Movie> {
//        return Firebase.firestore.collection(COLLECTION_MOVIES)
//            .get()
//            .await()
//            .toObjects(Movie::class.java)
//    }
}