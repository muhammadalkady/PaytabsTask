package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
import kady.muhammad.paytabstask.presentation.ext.callAPI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repo: Repo,
    private val domainCharacterToUICharacter: DomainCharacterToUICharacter
) : ViewModel() {

    private val _result: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    val result: StateFlow<Result> get() = _result

    init {
        charactersList(offset = 0)
    }

    fun charactersList(offset: Int) {
        viewModelScope.launch {
            callAPI(domainCharacterToUICharacter) { repo.charactersList(offset) }
                .collect {
                    _result.value = it
                }
        }
    }
}