package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kady.muhammad.paytabstask.app.Result
import kady.muhammad.paytabstask.domain.IRepo
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacterMapper
import kady.muhammad.paytabstask.presentation.entities.UICharacterList
import kady.muhammad.paytabstask.presentation.ext.callAPI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CharactersViewModel(
    private val cc: CoroutineContext = EmptyCoroutineContext,
    private val repo: IRepo,
    private val domainMapper: DomainCharacterToUICharacterMapper,
    initCall: Boolean = true,
    offset: Int
) : ViewModel() {

    private val _result: MutableStateFlow<UICharacterList> = MutableStateFlow(UICharacterList.EMPTY)
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _error: MutableStateFlow<String> = MutableStateFlow("")
    val result: StateFlow<UICharacterList> get() = _result
    val loading: StateFlow<Boolean> get() = _loading
    val error: StateFlow<String> get() = _error

    init {
        if (initCall)
            charactersList(page = offset)
    }

    fun charactersList(page: Int) {
        viewModelScope.launch(context = cc) {
            callAPI(domainMapper) { repo.charactersList(page) }
                .collect {
                    _loading.value = it is Result.Loading
                    _error.value = if (it !is Result.Error) "" else it.message
                    when (it) {
                        is Result.Success -> _result.value =
                            UICharacterList(_result.value.items + it.data.items, it.data.page)
                        else -> {}
                    }
                }
        }
    }
}
