package br.com.network.rest

import br.com.network.dto.CheckInDTO
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckInRest {
    @POST("api/checkin")
    fun post(@Body checkInDTO: CheckInDTO): Call<ResponseBody>
}