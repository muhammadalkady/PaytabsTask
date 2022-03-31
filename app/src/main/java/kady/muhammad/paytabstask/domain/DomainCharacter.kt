package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.app.Mapper
import kady.muhammad.paytabstask.data.network.DataCharacters

data class DomainCharacter(val name: String, val image: String)

class DataCharactersToDomainCharacters : Mapper<DataCharacters, List<DomainCharacter>> {

    override fun map(input: DataCharacters): List<DomainCharacter> {
        return input.data.character.map {
            DomainCharacter(
                it.name,
                "${it.thumbnail.path}.${it.thumbnail.extension}"
            )
        }
    }

}
