package kady.muhammad.paytabstask.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kady.muhammad.paytabstask.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.lang.Exception

class MarvelAPI(baseURL: String = BuildConfig.BASE_URL) {
    /**
     * Marvel API body content type.
     * */
    private val contentType = "application/json".toMediaType()

    /**
     * Logging interceptor for debugging
     * */
    private val logging =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }

    /**
     * OkHttp client with logging interceptor in debugging only
     * */
    private val client: OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG)
                addInterceptor(logging)
                    .addNetworkInterceptor {
                        val response = it.proceed(it.request())
                        if (response.code != 200) {
                            response.close()
                            throw Exception("Network error code: ${response.code}")
                        }
                        response
                    }
        }
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Build a retrofit instance with Marvel base URL and our OkHttp client
     * */
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseURL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    /**
     * Create Marvel service instance
     * */
    private val service = retrofit.create(MarvelService::class.java)


    /**
     * Get characters list from Marvel API
     * @param offset an offset to start from it.
     * @return CharactersListResponse parsed from json.
     * */
    suspend fun getCharacterList(offset: Int):
            DataCharacters {
        return service.getCharactersList(LIMIT, if (offset == 0) 0 else offset + 9)
    }

    companion object {
        /**
         * This is the max items that an API can return at one time.
         * */
        private const val LIMIT = 10
    }

}