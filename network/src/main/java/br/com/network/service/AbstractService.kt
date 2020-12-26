package br.com.network.service

import retrofit2.Retrofit

abstract class AbstractService<DTO, REST>(private val retrofit: Retrofit, private val cls: Class<REST>) {
    fun getService(): REST {
        return this.retrofit.create(this.cls)
    }
}