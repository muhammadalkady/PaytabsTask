package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.data.db.DBToDomainCharacterList
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.DataCharacters
import kady.muhammad.paytabstask.data.network.IMarvelAPI

class Repo(
    override val marvelAPI: IMarvelAPI,
    override val db: IDB,
    override val dataMapper: DataCharactersToDomainCharactersMapper,
    override val networkMapper: NetworkCharacterToDBCharacterMapper,
) : IRepo {

    override suspend fun charactersList(page: Int, fromCacheFirst: Boolean): DomainCharacterList {
        println("charactersList $page")
        val apiCallBlock = suspend {
            val input: DataCharacters = marvelAPI.getCharacterList(page = page)
            db.putCharacters(input.data.character.map(networkMapper::map))
            dataMapper.map(input)
        }
        val fromCacheBlock = suspend {
            val result = db.getCharacters(page)
            if (result.isNotEmpty()) DBToDomainCharacterList(page).map(result)
            else apiCallBlock()
        }
        return fromCacheOrElse(
            fromCacheFirst = fromCacheFirst,
            fromCacheBlock = fromCacheBlock,
            fromAPIBlock = apiCallBlock
        )
    }

}