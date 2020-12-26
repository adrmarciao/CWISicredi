package br.com.cwisicredi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cwisicredi.repository.EventRepository
import br.com.network.dto.EventDTO
import br.com.network.exception.NetworkDataException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(private val eventRepository: EventRepository): ViewModel() {

    private val eventLiveData: MutableLiveData<List<EventDTO>> by lazy { MutableLiveData() }

    fun getEvents(): MutableLiveData<List<EventDTO>> {
        GlobalScope.launch {
            try {
                eventLiveData.postValue(eventRepository.getEvents().await())
            } catch (ex: NetworkDataException) {
                eventLiveData.postValue(emptyList())
            }
        }
        return eventLiveData;
    }
}