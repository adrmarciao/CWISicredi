package br.com.cwisicredi.repository.impl

import br.com.cwisicredi.repository.EventRepository
import br.com.network.dto.EventDTO
import br.com.network.exception.NetworkDataException
import br.com.network.service.EventService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.await

class EventRepositoryImpl(private val eventService: EventService) : EventRepository {
    override suspend fun getEvents(): Deferred<List<EventDTO>> = GlobalScope.async {
        try {
            return@async eventService.getService().getAll().await()
        } catch (e: Exception) {
            throw NetworkDataException(e)
        }
    }

    override suspend fun getEvent(id: Long): Deferred<EventDTO> = GlobalScope.async {
        try {
            return@async eventService.getService().get(id).await()
        } catch (e: Exception) {
            throw NetworkDataException(e)
        }
    }
}