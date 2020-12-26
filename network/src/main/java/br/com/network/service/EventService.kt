package br.com.network.service

import br.com.network.dto.EventDTO
import br.com.network.rest.EventRest
import retrofit2.Retrofit

class EventService(retrofit: Retrofit) : AbstractService<EventDTO, EventRest>(retrofit,
    EventRest::class.java)