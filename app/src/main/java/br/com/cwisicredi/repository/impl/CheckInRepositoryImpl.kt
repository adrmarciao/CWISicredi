package br.com.cwisicredi.repository.impl

import br.com.cwisicredi.repository.CheckInRepository
import br.com.network.dto.CheckInDTO
import br.com.network.exception.NetworkDataException
import br.com.network.service.CheckInService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.await

class CheckInRepositoryImpl(private val checkInService: CheckInService): CheckInRepository {
    override suspend fun postCheckIn(checkInDTO: CheckInDTO): Deferred<ResponseBody> = GlobalScope.async {
        try {
            return@async checkInService.getService().post(checkInDTO).await()
        } catch (e: Exception) {
            throw NetworkDataException(e)
        }
    }
}