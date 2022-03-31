package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kady.muhammad.paytabstask.app.CharactersResult
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
import kady.muhammad.paytabstask.presentation.entities.UICharacterList
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

    private val _result: MutableStateFlow<CharactersResult> = MutableStateFlow(Result.Loading)
    val result: StateFlow<CharactersResult> get() = _result

    init {
        charactersList(offset = offset)
    }

    fun charactersList(offset: Int) {
        viewModelScope.launch(context = cc) {
            callAPI(domainCharacterToUICharacter) { repo.charactersList(offset) }
                .collect {
                    _result.value = _result.value + it
                }
        }
    }
}

private operator fun Result<UICharacterList>.plus(other: Result<UICharacterList>):
        Result<UICharacterList> {
    if (other !is Result.Success) return this
    return when (this) {
        is Result.Error -> other
        Result.Loading -> other
        is Result.Success -> Result.Success(
            UICharacterList(
                this.data.items + other.data.items,
                page = other.data.page
            )
        )
    }
}
