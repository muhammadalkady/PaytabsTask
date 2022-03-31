package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.network.MarvelAPI

class Repo(
    private val marvelAPI: MarvelAPI,
    private val mapper: DataCharactersToDomainCharacters
) {

    suspend fun charactersList(offset: Int): List<DomainCharacter> =
        mapper.map(marvelAPI.getCharacterList(offset = offset))

}