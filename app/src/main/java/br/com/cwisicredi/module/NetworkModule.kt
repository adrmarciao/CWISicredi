package br.com.cwisicredi.core.module

import br.com.network.service.EventService
import org.koin.dsl.module

val networkModule = module {
    single { EventService(get()) }
}