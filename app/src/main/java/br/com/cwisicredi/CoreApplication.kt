package br.com.cwisicredi

import android.app.Application
import br.com.cwisicredi.core.module.applicationModule
import br.com.cwisicredi.core.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class CoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoreApplication)
            androidFileProperties()
            modules(applicationModule, networkModule)
        }
    }

}