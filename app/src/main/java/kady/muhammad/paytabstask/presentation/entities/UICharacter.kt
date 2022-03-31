package kady.muhammad.paytabstask.presentation.entities

import kady.muhammad.paytabstask.app.ListMapper
import kady.muhammad.paytabstask.domain.DomainCharacter

class UICharacter(val name: String, val image: String)

class DomainCharacterToUICharacter : ListMapper<DomainCharacter, UICharacter> {
    override fun map(input: List<DomainCharacter>): List<UICharacter> {
        return input.map { UICharacter(it.name, it.image) }
    }
}