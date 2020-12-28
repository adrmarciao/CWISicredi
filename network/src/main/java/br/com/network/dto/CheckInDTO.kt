package br.com.network.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CheckInDTO(val id: Long, val name: String, val email: String): Parcelable