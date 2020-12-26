package br.com.network.rest

import br.com.network.dto.EventDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventRest {
    @GET("api/events")
    fun getAll(): Call<List<EventDTO>>

    @GET("api/events/{id}")
    fun get(@Path("id") id: Long): Call<EventDTO>
}