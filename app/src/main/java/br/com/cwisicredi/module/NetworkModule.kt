package br.com.cwisicredi.module

import br.com.network.service.CheckInService
import br.com.network.service.EventService
import org.koin.dsl.module

val networkModule = module {
    single { EventService(get()) }
    single { CheckInService(get()) }
}