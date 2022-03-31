package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
import kady.muhammad.paytabstask.presentation.ext.callAPI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CharactersViewModel(
    private val cc: CoroutineContext = EmptyCoroutineContext,
    private val repo: Repo,
    private val domainCharacterToUICharacter: DomainCharacterToUICharacter,
    offset: Int
) : ViewModel() {

    private val _result: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    val result: StateFlow<Result> get() = _result

    init {
        charactersList(offset = offset)
    }

    @VisibleForTesting
    fun charactersList(offset: Int) {
        viewModelScope.launch(context = cc) {
            callAPI(domainCharacterToUICharacter) { repo.charactersList(offset) }
                .collect {
                    _result.value = it
                }
        }
    }
}