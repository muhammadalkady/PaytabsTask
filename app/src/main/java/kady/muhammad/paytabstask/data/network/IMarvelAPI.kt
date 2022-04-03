package kady.muhammad.paytabstask.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


interface IMarvelAPI {
    /**
     * Marvel API body content type.
     * */
    val contentType: MediaType get() = "application/json".toMediaType()

    /**
     * Logging interceptor for debugging
     * */
    val logging: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor()
            .apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }

    /**
     * Create Marvel service instance
     * */
    val service: MarvelService get() = retrofit.create(MarvelService::class.java)

    /**
     * OkHttp client with logging interceptor in debugging only
     * */
    val client: OkHttpClient

    val json: Json

    val baseURL: String

    /**
     * Build a retrofit instance with Marvel base URL and our OkHttp client
     * */
    val retrofit: Retrofit

    /**
     * Get characters list from Marvel API
     * @param offset an offset to start from it.
     * @return CharactersListResponse parsed from json.
     * */
    suspend fun getCharacterList(offset: Int): DataCharacters

    companion object {
        /**
         * This is the max items that an API can return at one time.
         * */
        const val LIMIT = 10
    }

}