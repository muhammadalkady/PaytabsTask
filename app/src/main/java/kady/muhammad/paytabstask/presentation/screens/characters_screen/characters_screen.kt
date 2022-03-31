package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kady.muhammad.paytabstask.presentation.ui.Loader
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.UICharacter
import kady.muhammad.paytabstask.presentation.entities.UICharacterList

@Suppress("UNCHECKED_CAST")
@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val value by viewModel.result.collectAsState(initial = Result.Loading)
    when (value) {
        is Result.Error -> Text(text = (value as Result.Error).message)
        Result.Loading -> Loader()
        is Result.Success<*> -> {
            val data = (value as Result.Success<UICharacterList>).data
            CharactersList(
                viewModel,
                data.items, data.page
            )
        }
    }
}

@Composable
fun CharactersList(viewModel: CharactersViewModel, characters: List<UICharacter>, page: Int) {
    LazyColumn {
        items(characters.size) { i ->
            CoilImage(
                characters[i].image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                circularReveal = CircularReveal(duration = 350),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = Color.Gray,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            if (i == characters.size - 1) {
                println("BOTTOM page $page")
                viewModel.charactersList(page.inc())
            }
        }
        item {
            Box(modifier = Modifier.height(100.dp)) {
                Loader()
            }
        }
    }
}
