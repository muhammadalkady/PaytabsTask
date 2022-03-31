package kady.muhammad.paytabstask.app

import android.app.Application
import kady.muhammad.paytabstask.data.network.MarvelAPI
import kady.muhammad.paytabstask.domain.DataCharactersToDomainCharacters
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacter
import kady.muhammad.paytabstask.presentation.screens.characters_screen.CharactersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

@Suppress("unused")
class PaytabsTaskApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PaytabsTaskApp)
            modules(appModule)
        }
    }
}

val appModule = module {
    val dataCharactersToDomainCharacters = DataCharactersToDomainCharacters()
    val domainCharacterToUICharacter = DomainCharacterToUICharacter()
    val marvelAPI = MarvelAPI()
    val repo = Repo(marvelAPI, dataCharactersToDomainCharacters)
    single { dataCharactersToDomainCharacters }
    single { domainCharacterToUICharacter }
    single { marvelAPI }
    single { repo }
    viewModel { CharactersViewModel(repo = repo, domainCharacterToUICharacter) }
}