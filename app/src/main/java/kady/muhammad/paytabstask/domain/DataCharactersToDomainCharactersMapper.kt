package kady.muhammad.paytabstask.domain

import kady.muhammad.paytabstask.app.IMapper
import kady.muhammad.paytabstask.data.network.DataCharacters

class DataCharactersToDomainCharactersMapper(private val pageLimit: Int) :
    IMapper<DataCharacters, DomainCharacterList> {

    override fun map(input: DataCharacters): DomainCharacterList {
        return DomainCharacterList(input.data.character.map {
            val image = "${it.thumbnail.path}.${it.thumbnail.extension}"
            DomainCharacter(
                it.name,
                if (image == NOT_FOUND_CHARACTER_IMAGE) "" else image
            )
        }, input.data.offset / pageLimit)
    }

    companion object {
        private const val NOT_FOUND_CHARACTER_IMAGE =
            "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
    }
}