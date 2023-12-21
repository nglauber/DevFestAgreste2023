package screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Book
import data.Publisher
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class BookListUiState(
    val books: List<Book> = emptyList()
)

class BooksListViewModel: StateScreenModel<BookListUiState>(BookListUiState()) {
    private val jsonUrl = "https://raw.githubusercontent.com/nglauber/dominando_android3/master/livros_novatec.json"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        updateBookList()
    }

    fun updateBookList() {
        screenModelScope.launch {
            val publisher = loadPublisher()
            val books = publisher.categories.flatMap { it.books }
            mutableState.update {
                it.copy(books = books)
            }
        }
    }

    private suspend fun loadPublisher(): Publisher {
        return try {
            httpClient.get(jsonUrl).body()
        } catch (e: NoTransformationFoundException) {
            val jsonString = httpClient.get(jsonUrl).body<String>()
            Json.decodeFromString(jsonString)
        }
    }

    override fun onDispose() {
        super.onDispose()
        httpClient.close()
    }
}