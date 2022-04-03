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
    page: Int
) : ViewModel() {

    private val _result: MutableStateFlow<UICharacterList> = MutableStateFlow(UICharacterList.EMPTY)
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _error: MutableStateFlow<String> = MutableStateFlow("")
    val result: StateFlow<UICharacterList> get() = _result
    val loading: StateFlow<Boolean> get() = _loading
    val error: StateFlow<String> get() = _error

    init {
        if (initCall)
            charactersList(page = page, fromCacheFirst = true)
    }

    fun charactersList(
        page: Int = if (result.value.items.isNotEmpty()) result.value.page.inc() else 1,
        fromCacheFirst: Boolean = true
    ) {
        viewModelScope.launch(context = cc) {
            callAPI(domainMapper) { repo.charactersList(page, fromCacheFirst) }
                .collect {
                    _loading.value = it is Result.Loading
                    sendError(it)
                    when (it) {
                        is Result.Success -> sendSuccess(it)
                        else -> {}
                    }
                }
        }
    }

    private fun sendSuccess(result: Result.Success<UICharacterList>) {
        _result.value =
            UICharacterList(_result.value.items + result.data.items, result.data.page)
    }

    private fun sendError(result: Result<UICharacterList>) {
        _error.value = if (result !is Result.Error) "" else result.message
    }
}
