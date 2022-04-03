package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.IMarvelAPI

interface IRepo {
    val marvelAPI: IMarvelAPI
    val db: IDB
    val dataMapper: DataCharactersToDomainCharactersMapper
    val networkMapper: NetworkCharacterToDBCharacterMapper
    suspend fun charactersList(page: Int, fromCacheFirst: Boolean = true): DomainCharacterList
    suspend fun <T> fromCacheOrElse(
        fromCacheFirst: Boolean,
        fromCacheBlock: suspend () -> T,
        fromAPIBlock: suspend () -> T
    ): T {
        return if (fromCacheFirst) fromCacheBlock() else fromAPIBlock()
    }
}