package kady.muhammad.paytabstask.data.network

import kady.muhammad.paytabstask.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("characters?apikey=${BuildConfig.API_KEY}&ts=$TS&hash=$HASH")
    suspend fun getCharactersList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): DataCharacters


    companion object {
        /**
         * Hash string. You can see how it is generated
         * @see <a href="https://developer.marvel.com/documentation/authorization">Marvel docs</a>
         * Should be removed from code in a real world app.
         * */
        private const val HASH = "96b78907a566d69806e6dc1889b683b3"
        private const val TS = "1"
    }
}