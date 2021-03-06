package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
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
    val isLoading: Boolean by viewModel.loading.collectAsState()
    val error: String by viewModel.error.collectAsState()
    val lazyColumnState = rememberLazyListState()
    LazyColumn(state = lazyColumnState) {
        items(characters.size) { i ->
            val character = characters[i]
            val canLoadMore = i == characters.size - 1 && !isLoading && error.isEmpty()
            Box {
                CharacterImage(url = character.image, name = character.name)
                CharacterName(
                    modifier = Modifier.align(Alignment.BottomStart),
                    name = character.name
                )
            }
            if (canLoadMore) viewModel.charactersList(fromCacheFirst = true)
        }
        if (isLoading) item { ListLoader() }
        if (error.isNotEmpty()) item {
            Error(error = error) {
                viewModel.charactersList(fromCacheFirst = true)
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
private fun CharacterName(modifier: Modifier = Modifier, name: String) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.5F))
            .padding(16.dp)
    ) {
        Text(modifier = modifier, text = name, textAlign = TextAlign.Center, fontSize = 18.sp)
    }
}

@Composable
private fun shimmerParams() = ShimmerParams(
    baseColor = MaterialTheme.colors.background,
    highlightColor = Color.Gray,
    durationMillis = 350,
    dropOff = 0.65f,
    tilt = 20f
)
