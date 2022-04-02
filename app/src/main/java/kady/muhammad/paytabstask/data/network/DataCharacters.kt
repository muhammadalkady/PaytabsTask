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
    @SerialName("code") val code: Int,
    @SerialName("data") val `data`: Data,
) : Parcelable {
    @Keep
    @Serializable
    @Parcelize
    data class Data(
        @SerialName("count") val count: Int,
        @SerialName("limit") val limit: Int,
        @SerialName("offset") val offset: Int,
        @SerialName("results") val character: List<Character>,
        @SerialName("total") val total: Int
    ) : Parcelable {
        @Keep
        @Serializable
        @Parcelize
        data class Character(
            @SerialName("id") val id: Int,
            @SerialName("name") val name: String,
            @SerialName("thumbnail") val thumbnail: Thumbnail,
        ) : Parcelable {
            @Keep
            @Serializable
            @Parcelize
            data class Thumbnail(
                @SerialName("extension") val extension: String,
                @SerialName("path") val path: String
            ) : Parcelable
        }
    }
}