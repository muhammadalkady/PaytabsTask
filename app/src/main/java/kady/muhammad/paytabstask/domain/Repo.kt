package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.DataCharacters
import kady.muhammad.paytabstask.data.network.IMarvelAPI

class Repo(
    override val marvelAPI: IMarvelAPI,
    override val db: IDB,
    override val dataMapper: DataCharactersToDomainCharactersMapper,
    override val networkMapper: NetworkCharacterToDBCharacterMapper,
) : IRepo {

    override suspend fun charactersList(offset: Int): DomainCharacterList {
        val input: DataCharacters = marvelAPI.getCharacterList(offset = offset)
        db.putCharacters(input.data.character.map(networkMapper::map))
        return dataMapper.map(input)
    }

}