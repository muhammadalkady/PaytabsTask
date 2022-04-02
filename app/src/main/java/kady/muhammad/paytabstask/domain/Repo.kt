package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacter
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.DataCharacters
import kady.muhammad.paytabstask.data.network.MarvelAPI

class Repo(
    private val marvelAPI: MarvelAPI,
    private val db: IDB,
    private val domainMapper: DataCharactersToDomainCharacters,
    private val dbMapper: NetworkCharacterToDBCharacter,
) {

    suspend fun charactersList(offset: Int): DomainCharacterList {
        val input: DataCharacters = marvelAPI.getCharacterList(offset = offset)
        db.putCharacters(input.data.character.map(dbMapper::map))
        return domainMapper.map(input)
    }

}