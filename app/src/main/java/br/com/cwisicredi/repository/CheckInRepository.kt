package br.com.cwisicredi.repository

import br.com.network.dto.CheckInDTO
import kotlinx.coroutines.Deferred
import okhttp3.Response
import okhttp3.ResponseBody

interface CheckInRepository {
    suspend fun postCheckIn(checkInDTO: CheckInDTO): Deferred<ResponseBody>
}