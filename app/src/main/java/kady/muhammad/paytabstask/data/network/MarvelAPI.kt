package kady.muhammad.paytabstask.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kady.muhammad.paytabstask.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


class MarvelAPI(baseURL: String = BuildConfig.BASE_URL) {
    private val logging =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val contentType = "application/json".toMediaType()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseURL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    private val service = retrofit.create(MarvelService::class.java)

    suspend fun getCharacterList(limit: Int = 10, offset: Int): CharactersListResponse {
        return service.getCharactersList(limit, offset)
    }
}