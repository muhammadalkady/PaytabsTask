package kady.muhammad.paytabstask.data.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Keep
@Serializable
@Parcelize
data class DataCharacters(
    @SerialName("attributionHTML")
    val attributionHTML: String,
    @SerialName("attributionText")
    val attributionText: String,
    @SerialName("code")
    val code: Int,
    @SerialName("copyright")
    val copyright: String,
    @SerialName("data")
    val `data`: Data,
    @SerialName("etag")
    val etag: String,
    @SerialName("status")
    val status: String
) : Parcelable {
    @Keep
    @Serializable
    @Parcelize
    data class Data(
        @SerialName("count")
        val count: Int,
        @SerialName("limit")
        val limit: Int,
        @SerialName("offset")
        val offset: Int,
        @SerialName("results")
        val character: List<Character>,
        @SerialName("total")
        val total: Int
    ) : Parcelable {
        @Keep
        @Serializable
        @Parcelize
        data class Character(
            @SerialName("comics")
            val comics: Comics,
            @SerialName("description")
            val description: String,
            @SerialName("events")
            val events: Events,
            @SerialName("id")
            val id: Int,
            @SerialName("modified")
            val modified: String,
            @SerialName("name")
            val name: String,
            @SerialName("resourceURI")
            val resourceURI: String,
            @SerialName("series")
            val series: Series,
            @SerialName("stories")
            val stories: Stories,
            @SerialName("thumbnail")
            val thumbnail: Thumbnail,
            @SerialName("urls")
            val urls: List<Url>
        ) : Parcelable {
            @Keep
            @Serializable
            @Parcelize
            data class Comics(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String,
                @SerialName("items")
                val items: List<Item>,
                @SerialName("returned")
                val returned: Int
            ) : Parcelable {
                @Keep
                @Serializable
                @Parcelize
                data class Item(
                    @SerialName("name")
                    val name: String,
                    @SerialName("resourceURI")
                    val resourceURI: String
                ) : Parcelable
            }

            @Keep
            @Serializable
            @Parcelize
            data class Events(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String,
                @SerialName("items")
                val items: List<Item>,
                @SerialName("returned")
                val returned: Int
            ) : Parcelable {
                @Keep
                @Serializable
                @Parcelize
                data class Item(
                    @SerialName("name")
                    val name: String,
                    @SerialName("resourceURI")
                    val resourceURI: String
                ) : Parcelable
            }

            @Keep
            @Serializable
            @Parcelize
            data class Series(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String,
                @SerialName("items")
                val items: List<Item>,
                @SerialName("returned")
                val returned: Int
            ) : Parcelable {
                @Keep
                @Serializable
                @Parcelize
                data class Item(
                    @SerialName("name")
                    val name: String,
                    @SerialName("resourceURI")
                    val resourceURI: String
                ) : Parcelable
            }

            @Keep
            @Serializable
            @Parcelize
            data class Stories(
                @SerialName("available")
                val available: Int,
                @SerialName("collectionURI")
                val collectionURI: String,
                @SerialName("items")
                val items: List<Item>,
                @SerialName("returned")
                val returned: Int
            ) : Parcelable {
                @Keep
                @Serializable
                @Parcelize
                data class Item(
                    @SerialName("name")
                    val name: String,
                    @SerialName("resourceURI")
                    val resourceURI: String,
                    @SerialName("type")
                    val type: String
                ) : Parcelable
            }

            @Keep
            @Serializable
            @Parcelize
            data class Thumbnail(
                @SerialName("extension")
                val extension: String,
                @SerialName("path")
                val path: String
            ) : Parcelable

            @Keep
            @Serializable
            @Parcelize
            data class Url(
                @SerialName("type")
                val type: String,
                @SerialName("url")
                val url: String
            ) : Parcelable
        }
    }
}