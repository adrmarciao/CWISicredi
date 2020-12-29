package br.com.cwisicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cwisicredi.repository.CheckInRepository
import br.com.cwisicredi.repository.EventRepository
import br.com.network.dto.CheckInDTO
import br.com.network.exception.NetworkDataException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel(private val eventRepository: EventRepository,
                      private val checkInRepository: CheckInRepository): ViewModel() {

    val invalidName by lazy { MutableLiveData<Boolean>() }
    val invalidEmail by lazy { MutableLiveData<Boolean>() }

    fun validateRequiredInfoAndNotifyError(name: String, email: String): Boolean {
        if (name.isNotEmpty() && email.isNotEmpty()) {
            return true
        }
        if (name.isEmpty()) {
            invalidName.postValue(true)
        }
        if (email.isEmpty()) {
            invalidEmail.postValue(true)
        }
        return false
    }

    fun checkIn(eventId: Long, name: String, email: String): MutableLiveData<Any> {
        val checkInLiveData = MutableLiveData<Any>()
        GlobalScope.launch {
            try {
                checkInLiveData.postValue(checkInRepository
                    .postCheckIn(CheckInDTO(eventId, name, email)).await())
            } catch (ex: NetworkDataException) {
                checkInLiveData.postValue(ex)
            }
        }
        return checkInLiveData
    }
}