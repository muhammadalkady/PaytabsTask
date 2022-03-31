package kady.muhammad.paytabstask.presentation.screens.characters_screen

import androidx.lifecycle.ViewModel
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.domain.Result
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
import kady.muhammad.paytabstask.presentation.ext.callAPI
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(
    private val repo: Repo,
    private val domainCharacterToUICharacter: DomainCharacterToUICharacter
) : ViewModel() {

    fun charactersList(offset: Int): Flow<Result> =
        callAPI(domainCharacterToUICharacter) { repo.charactersList(offset) }
}