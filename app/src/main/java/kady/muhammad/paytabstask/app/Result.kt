package kady.muhammad.paytabstask.app

import kady.muhammad.paytabstask.presentation.entities.UICharacterList

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

typealias CharactersResult = Result<UICharacterList>