package br.com.cwisicredi.module

import br.com.cwisicredi.scope.NetworkScope
import br.com.cwisicredi.scope.UiScope
import kotlinx.coroutines.Job
import org.koin.dsl.module

val coroutinesModule = module {
    single<Job> { Job() }
    single { UiScope(get()) }
    single { NetworkScope(get()) }
}