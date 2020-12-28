package br.com.cwisicredi.module

import br.com.network.RetrofitInitialize
import org.koin.dsl.module

val applicationModule = module {
    single { RetrofitInitialize().init(get()) }
}