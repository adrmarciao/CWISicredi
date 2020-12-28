package br.com.network.service

import br.com.network.dto.CheckInDTO
import br.com.network.rest.CheckInRest
import retrofit2.Retrofit

class CheckInService(retrofit: Retrofit): AbstractService<CheckInDTO,
        CheckInRest>(retrofit, CheckInRest::class.java)