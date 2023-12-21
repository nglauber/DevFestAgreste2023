package screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.Book
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class BooksListScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            BooksListViewModel()
        }
        val uiState by viewModel.state.collectAsState()
        LazyColumn {
            items(uiState.books) {
                BookListItem(it)
            }
        }
    }

    @Composable
    fun BookListItem(book: Book) {
        val navigator = LocalNavigator.currentOrThrow
        Row(
            Modifier.padding(8.dp).clickable {
                navigator.push(BooksDetailsScreen(book))
            },
        ) {
            KamelImage(
                resource = asyncPainterResource(book.coverUrl),
                contentDescription = "Capa do livro ${book.title}",
                modifier = Modifier.width(72.dp).height(92.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(.7f)) {
                Text(book.title, style = MaterialTheme.typography.h6)
                Text(book.author)
                Text("Ano: ${book.year} | Pages: ${book.pages}")
            }
        }
    }
}