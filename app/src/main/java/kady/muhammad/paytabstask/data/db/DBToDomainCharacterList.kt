package kady.muhammad.paytabstask.data.db

import kady.muhammad.paytabstask.app.IMapper
import kady.muhammad.paytabstask.domain.DomainCharacter
import kady.muhammad.paytabstask.domain.DomainCharacterList

class DBToDomainCharacterList(private val page: Int) :
    IMapper<List<DBCharacter>, DomainCharacterList> {
    override fun map(input: List<DBCharacter>): DomainCharacterList {
        return DomainCharacterList(input.map { DomainCharacter(it.name, it.image) }, page)
    }
}