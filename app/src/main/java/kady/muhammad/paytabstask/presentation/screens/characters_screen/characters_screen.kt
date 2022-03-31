package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kady.muhammad.paytabstask.presentation.ui.Loader
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.UICharacter

@Suppress("UNCHECKED_CAST")
@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val value by viewModel.result.collectAsState(initial = Result.Loading)
    when (value) {
        is Result.Error -> Text(text = (value as Result.Error).message)
        Result.Loading -> Loader()
        is Result.Success<*> -> CharactersList((value as Result.Success<List<UICharacter>>).data)
    }
}

@Composable
fun CharactersList(characters: List<UICharacter>) {
    LazyColumn {
        items(characters) {
            AsyncImage(
                model = it.image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
        item {
            Box(modifier = Modifier.height(100.dp)) {
                Loader()
            }
        }
    }
}

