package kady.muhammad.paytabstask.presentation.entities

import kady.muhammad.paytabstask.app.Mapper
import kady.muhammad.paytabstask.domain.DomainCharacterList

class UICharacter(val name: String, val image: String)
class UICharacterList(val items: List<UICharacter>, val page: Int)

class DomainCharacterToUICharacter : Mapper<DomainCharacterList, UICharacterList> {
    override fun map(input: DomainCharacterList): UICharacterList {
        return UICharacterList(input.items.map { UICharacter(it.name, it.image) }, input.offset)
    }
}