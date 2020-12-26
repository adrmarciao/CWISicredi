package br.com.network.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
@Parcelize
class EventDTO(val date: Long, val description: String, val image: String,
                    val longitude: Double, val latitude: Double, val price: BigDecimal,
                    val title: String, val id: Long): Parcelable