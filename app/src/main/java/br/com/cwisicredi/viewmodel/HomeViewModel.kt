package br.com.cwisicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cwisicredi.repository.EventRepository
import br.com.cwisicredi.scope.UiScope
import br.com.network.dto.EventDTO
import br.com.network.exception.NetworkDataException
import kotlinx.coroutines.launch

class HomeViewModel(private val uiScope: UiScope,
                    private val eventRepository: EventRepository): ViewModel() {

    val eventLiveData: MutableLiveData<List<EventDTO>> by lazy { MutableLiveData() }
    val eventErrorMsgLiveData: MutableLiveData<List<EventDTO>> by lazy { MutableLiveData() }
    fun getEvents() {
        uiScope.launch {
            try {
                val eventList = eventRepository.getEvents().await()
                eventLiveData.postValue(eventList)
            } catch (ex: NetworkDataException) {
                eventErrorMsgLiveData.postValue(emptyList())
            }
        }
    }
}