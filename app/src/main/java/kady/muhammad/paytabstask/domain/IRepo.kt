package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.IMarvelAPI

interface IRepo {
    val marvelAPI: IMarvelAPI
    val db: IDB
    val dataMapper: DataCharactersToDomainCharactersMapper
    val networkMapper: NetworkCharacterToDBCharacterMapper
    suspend fun charactersList(offset: Int): DomainCharacterList
}