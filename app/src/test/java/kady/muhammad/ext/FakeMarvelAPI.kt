package kady.muhammad.ext

import kady.muhammad.paytabstask.data.network.DataCharacters
import kady.muhammad.paytabstask.data.network.IMarvelAPI
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class FakeMarvelAPI : IMarvelAPI {

    override val client: OkHttpClient = OkHttpClient()
    override val json: Json = Json
    override val baseURL: String = "https://example.com"
    override val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .build()

    override suspend fun getCharacterList(page: Int): DataCharacters {
        return DataCharacters(
            200, DataCharacters.Data(
                1, 1, page,
                (1..IMarvelAPI.LIMIT).map {
                    DataCharacters.Data.Character(
                        it + (page * IMarvelAPI.LIMIT), "name-offset-$page",
                        DataCharacters.Data.Character.Thumbnail(
                            "jpg",
                            "https://example.com/images"
                        )
                    )
                }, total = 1000
            )
        )
    }
}