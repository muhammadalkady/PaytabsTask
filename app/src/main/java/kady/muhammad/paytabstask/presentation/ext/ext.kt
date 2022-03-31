package kady.muhammad.paytabstask.presentation.ext

import kady.muhammad.paytabstask.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

fun <T> callAPI(
    block: suspend () -> T,
): Flow<Result> = flow {
    emit(Result.Loading)
    emit(Result.Success(block()))
}.catch { e -> emit(Result.Error(e.message ?: "UnKnown")) }
