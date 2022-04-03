package kady.muhammad.ext

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.DataCharacters
import kady.muhammad.paytabstask.data.network.IMarvelAPI
import kady.muhammad.paytabstask.domain.DataCharactersToDomainCharactersMapper
import kady.muhammad.paytabstask.domain.DomainCharacterList
import kady.muhammad.paytabstask.domain.IRepo

class FakeRepo(
    override val db: IDB = FakeDB(),
    override val marvelAPI: IMarvelAPI = FakeMarvelAPI()
) : IRepo {

    override val dataMapper: DataCharactersToDomainCharactersMapper =
        DataCharactersToDomainCharactersMapper()
    override val networkMapper: NetworkCharacterToDBCharacterMapper =
        NetworkCharacterToDBCharacterMapper()

    override suspend fun charactersList(page: Int, fromCacheFirst: Boolean): DomainCharacterList {
        val result: DataCharacters = marvelAPI.getCharacterList(page)
        db.putCharacters(result.data.character.map(networkMapper::map))
        return dataMapper.map(result)
    }
}