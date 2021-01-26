package br.com.cwisicredi.repository.impl

import br.com.cwisicredi.repository.EventRepository
import br.com.cwisicredi.scope.NetworkScope
import br.com.network.dto.EventDTO
import br.com.network.exception.NetworkDataException
import br.com.network.service.EventService
import kotlinx.coroutines.*
import retrofit2.await

class EventRepositoryImpl(private val networkScope: NetworkScope, private val eventService: EventService) : EventRepository {
    override suspend fun getEvents(): Deferred<List<EventDTO>> = networkScope.async {
        try {
            return@async eventService.getService().getAll().await()
        } catch (e: Exception) {
            throw NetworkDataException(e)
        }
    }

    override suspend fun getEvent(id: Long): Deferred<EventDTO> = networkScope.async {
        try {
            return@async eventService.getService().get(id).await()
        } catch (e: Exception) {
            throw NetworkDataException(e)
        }
    }
}