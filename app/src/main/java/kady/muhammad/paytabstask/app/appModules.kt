package kady.muhammad.paytabstask.app

import kady.muhammad.paytabstask.data.NetworkCharacterToDBCharacterMapper
import kady.muhammad.paytabstask.data.db.DB
import kady.muhammad.paytabstask.data.db.IDB
import kady.muhammad.paytabstask.data.network.IMarvelAPI
import kady.muhammad.paytabstask.data.network.MarvelAPI
import kady.muhammad.paytabstask.domain.DataCharactersToDomainCharactersMapper
import kady.muhammad.paytabstask.domain.IRepo
import kady.muhammad.paytabstask.domain.Repo
import kady.muhammad.paytabstask.presentation.entities.DomainCharacterToUICharacterMapper
import kady.muhammad.paytabstask.presentation.screens.characters_screen.CharactersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DataCharactersToDomainCharactersMapper() }
    single { DomainCharacterToUICharacterMapper() }
    single { NetworkCharacterToDBCharacterMapper() }
    single<IMarvelAPI> { MarvelAPI() }
    single {
        val db: IDB = DB()
        db.init(androidContext())
        db
    }
    single<IRepo> { Repo(get(), get(), get(), get()) }
    viewModel { CharactersViewModel(repo = get(), domainMapper = get(), offset = 0) }
}