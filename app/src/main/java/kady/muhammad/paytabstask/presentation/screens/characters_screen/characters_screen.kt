package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kady.muhammad.paytabstask.R
import kady.muhammad.paytabstask.presentation.ui.Loader
import kady.muhammad.paytabstask.presentation.entities.UICharacterList

@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val charactersList: UICharacterList by viewModel.result.collectAsState()
    val characters = charactersList.items
    val page = charactersList.page
    val isLoading: Boolean by viewModel.loading.collectAsState()
    val error: String by viewModel.error.collectAsState()
    val lazyColumnState = rememberLazyListState()
    LazyColumn(state = lazyColumnState) {
        items(characters.size) { i ->
            val character = characters[i]
            val canLoadMore = i == characters.size - 1 && !isLoading && error.isEmpty()
            CharacterImage(url = character.image, name = character.name)
            if (canLoadMore) viewModel.charactersList(page.inc())
        }
        if (isLoading) item { ListLoader() }
        if (error.isNotEmpty()) item {
            Error(error = error) {
                viewModel.charactersList(page.inc())
            }
        }
    }
}

@Composable
private fun Error(modifier: Modifier = Modifier, error: String, doOnRetry: () -> Unit = {}) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = error, textAlign = TextAlign.Center
        )
        Button(onClick = doOnRetry) { Text(text = "RETRY") }
    }

}


@Composable
private fun ListLoader() {
    Box(modifier = Modifier.height(300.dp)) { Loader() }
}

@Composable
private fun CharacterImage(modifier: Modifier = Modifier, url: String, name: String) {
    CoilImage(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        imageModel = url,
        error = ImageBitmap.imageResource(id = R.drawable.character_not_found),
        contentScale = ContentScale.Crop,
        shimmerParams = shimmerParams(),
        circularReveal = CircularReveal(duration = 350),
        contentDescription = name,
    )
}

@Composable
private fun shimmerParams() = ShimmerParams(
    baseColor = MaterialTheme.colors.background,
    highlightColor = Color.Gray,
    durationMillis = 350,
    dropOff = 0.65f,
    tilt = 20f
)
