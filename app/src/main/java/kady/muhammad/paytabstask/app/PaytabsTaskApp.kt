package kady.muhammad.paytabstask.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

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
