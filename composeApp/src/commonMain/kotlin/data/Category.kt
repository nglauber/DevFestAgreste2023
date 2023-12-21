package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("categoria")
    val name: String,
    @SerialName("livros")
    val books: List<Book>
)