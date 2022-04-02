package kady.muhammad.paytabstask.presentation.ext

import kady.muhammad.paytabstask.app.Mapper
import kady.muhammad.paytabstask.app.Result
import kotlinx.coroutines.flow.*

fun <T, O> callAPI(
    mapper: Mapper<T, O>,
    block: suspend () -> T,
): Flow<Result<O>> = flow {
    try {
        emit(Result.Loading)
        emit(Result.Success(mapper.map(block())))
    } catch (e: Throwable) {
        emit(Result.Error(e.message ?: "UnKnown"))
    }
}
