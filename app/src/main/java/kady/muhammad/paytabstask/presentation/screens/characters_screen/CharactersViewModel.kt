package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.lifecycle.ViewModel
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.domain.Result
import kady.muhammad.paytabstask.presentation.ext.callAPI
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(private val repo: Repo) : ViewModel() {

    fun charactersList(offset: Int): Flow<Result> = callAPI { repo.charactersList(offset) }
}