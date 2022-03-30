package kady.muhammad.paytabstask.data.network

import kady.muhammad.paytabstask.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

private const val HASH = "96b78907a566d69806e6dc1889b683b3"
private const val TS = "1"

interface MarvelService {
    @GET("characters?apikey=${BuildConfig.API_KEY}&ts=$TS&hash=$HASH")
    suspend fun getCharactersList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): CharactersListResponse
}