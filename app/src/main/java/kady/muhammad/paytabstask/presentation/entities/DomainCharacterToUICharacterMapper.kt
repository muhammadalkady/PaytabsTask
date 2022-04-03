package kady.muhammad.paytabstask.presentation.entities

import kady.muhammad.paytabstask.app.IMapper
import kady.muhammad.paytabstask.domain.DomainCharacterList

class DomainCharacterToUICharacterMapper : IMapper<DomainCharacterList, UICharacterList> {
    override fun map(input: DomainCharacterList): UICharacterList {
        return UICharacterList(input.items.map { UICharacter(it.name, it.image) }, input.page)
    }
}