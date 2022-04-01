package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.app.Mapper
import kady.muhammad.paytabstask.data.network.DataCharacters

data class DomainCharacter(val name: String, val image: String)
data class DomainCharacterList(val items: List<DomainCharacter>, val offset: Int)

class DataCharactersToDomainCharacters : Mapper<DataCharacters, DomainCharacterList> {

    override fun map(input: DataCharacters): DomainCharacterList {
        return DomainCharacterList(input.data.character.map {
            val image = "${it.thumbnail.path}.${it.thumbnail.extension}"
            DomainCharacter(
                it.name,
                if (image == NOT_FOUND_CHARACTER_IMAGE) "" else image
            )
        }, input.data.offset)
    }

    companion object {
        private const val NOT_FOUND_CHARACTER_IMAGE =
            "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
    }
}
