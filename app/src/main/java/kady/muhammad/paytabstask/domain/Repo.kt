package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.network.CharactersListResponse
import kady.muhammad.paytabstask.data.network.MarvelAPI

class Repo(private val marvelAPI: MarvelAPI) {

    suspend fun charactersList(offset: Int): CharactersListResponse =
        marvelAPI.getCharacterList(offset = offset)

}