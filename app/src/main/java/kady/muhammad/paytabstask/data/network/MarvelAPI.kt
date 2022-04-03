package kady.muhammad.paytabstask.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kady.muhammad.paytabstask.BuildConfig
import kady.muhammad.paytabstask.data.network.IMarvelAPI.Companion.PAGE_LIMIT
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.Exception

class MarvelAPI(override val baseURL: String = BuildConfig.BASE_URL) : IMarvelAPI {
    /**
     * OkHttp client with logging interceptor in debugging only
     * */
    override val client: OkHttpClient = OkHttpClient.Builder()
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

    override val json = Json { ignoreUnknownKeys = true }

    /**
     * Build a retrofit instance with Marvel base URL and our OkHttp client
     * */
    override val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseURL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    /**
     * Get characters list from Marvel API
     * @param page an offset to start from it.
     * @return CharactersListResponse parsed from json.
     * */
    override suspend fun getCharacterList(page: Int):
            DataCharacters {
        return service.getCharactersList(PAGE_LIMIT, pageToOffset(page))
    }

}