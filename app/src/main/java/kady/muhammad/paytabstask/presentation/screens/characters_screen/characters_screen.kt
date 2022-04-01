package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kady.muhammad.paytabstask.R
import kady.muhammad.paytabstask.presentation.ui.Loader
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.UICharacter
import kady.muhammad.paytabstask.presentation.entities.UICharacterList

@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val value by viewModel.result.collectAsState(initial = Result.Loading)
    when (value) {
        is Result.Error -> Text(text = (value as Result.Error).message)
        Result.Loading -> Loader()
        is Result.Success -> {
            val data = (value as Result.Success<UICharacterList>).data
            CharactersList(viewModel, data.items, data.page)
        }
    }
}

@Composable
fun CharactersList(viewModel: CharactersViewModel, characters: List<UICharacter>, page: Int) {
    LazyColumn {
        items(characters.size) { i ->
            val character = characters[i]
            CharacterImage(url = character.image, name = character.name)
            if (i == characters.size - 1) {
                viewModel.charactersList(page.inc())
            }
        }
        item { ListLoader() }
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
