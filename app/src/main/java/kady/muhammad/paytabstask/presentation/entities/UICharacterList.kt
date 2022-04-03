package kady.muhammad.paytabstask.presentation.entities

class UICharacterList(val items: List<UICharacter>, val page: Int) {
    companion object {
        val EMPTY = UICharacterList(emptyList(), 1)
    }
}