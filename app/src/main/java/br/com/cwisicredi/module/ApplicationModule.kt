package br.com.cwisicredi.module

import br.com.cwisicredi.repository.CheckInRepository
import br.com.cwisicredi.repository.EventRepository
import br.com.cwisicredi.repository.impl.CheckInRepositoryImpl
import br.com.cwisicredi.repository.impl.EventRepositoryImpl
import br.com.cwisicredi.view.adapter.EventsAdapter
import br.com.cwisicredi.viewmodel.DetailViewModel
import br.com.cwisicredi.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    factory { EventsAdapter(get()) }
    factory<EventRepository> { EventRepositoryImpl(get(),get()) }
    factory<CheckInRepository> { CheckInRepositoryImpl(get()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
}