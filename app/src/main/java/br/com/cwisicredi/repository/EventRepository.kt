package br.com.cwisicredi.repository

import br.com.network.dto.EventDTO
import br.com.network.exception.NetworkDataException
import kotlinx.coroutines.Deferred
import kotlin.jvm.Throws

interface EventRepository {
    @Throws(NetworkDataException::class)
    suspend fun getEvents(): Deferred<List<EventDTO>>
    suspend fun getEvent(id: Long): Deferred<EventDTO>
}