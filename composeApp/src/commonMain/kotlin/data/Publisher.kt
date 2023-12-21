package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Publisher(
    @SerialName("novatec")
    val categories: List<Category>
)