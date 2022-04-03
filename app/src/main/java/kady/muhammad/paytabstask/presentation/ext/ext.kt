package kady.muhammad.paytabstask.presentation.ext

import kady.muhammad.paytabstask.app.IMapper
import kady.muhammad.paytabstask.app.Result
import kotlinx.coroutines.flow.*

fun <T, O> callAPI(
    iMapper: IMapper<T, O>,
    block: suspend () -> T,
): Flow<Result<O>> = flow {
    try {
        emit(Result.Loading)
        emit(Result.Success(iMapper.map(block())))
    } catch (e: Throwable) {
        //TODO map errors to user readable format.
        emit(Result.Error(e.message ?: "Unknown Error"))
    }
}
