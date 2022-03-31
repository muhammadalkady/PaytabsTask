package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.app.Mapper
import kady.muhammad.paytabstask.data.network.DataCharacters

data class DomainCharacter(val name: String, val image: String)
data class DomainCharacterList(val items: List<DomainCharacter>, val offset: Int)

class DataCharactersToDomainCharacters : Mapper<DataCharacters, DomainCharacterList> {

    override fun map(input: DataCharacters): DomainCharacterList {
        return DomainCharacterList(input.data.character.map {
            DomainCharacter(
                it.name,
                "${it.thumbnail.path}.${it.thumbnail.extension}"
            )
        }, input.data.offset)
    }

}
