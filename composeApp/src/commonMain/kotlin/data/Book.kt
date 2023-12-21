package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("ano")
    val year: Int,
    @SerialName("autor")
    val author: String,
    @SerialName("capa")
    val coverUrl: String,
    @SerialName("paginas")
    val pages: Int,
    @SerialName("titulo")
    val title: String
)