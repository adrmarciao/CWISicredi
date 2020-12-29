package br.com.cwisicredi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.cwisicredi.repository.EventRepository
import br.com.network.dto.EventDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var eventRepository: EventRepository


    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        eventRepository = Mockito.mock(EventRepository::class.java)

        homeViewModel = HomeViewModel(eventRepository)
    }

    @Test
    fun getEventsNoEvent() = runBlocking {
        Mockito.lenient().`when`(eventRepository.getEvents()).thenReturn(async { ArrayList() })
        homeViewModel.getEvents()
        homeViewModel.eventErrorMsgLiveData.observeForever {
            Assert.assertEquals(0, it?.size)
        }
    }

    @Test
    fun getEventsWithEvent() = runBlocking {
        val events = ArrayList<EventDTO>()
        events.add(EventDTO(0,"", "", 0.0,0.0,
            BigDecimal(10), "", 0))
        Mockito.lenient().`when`(eventRepository.getEvents()).thenReturn(async { ArrayList(events) })
        homeViewModel.getEvents()
        homeViewModel.eventLiveData.observeForever {
            Assert.assertEquals(1, it?.size)
        }
    }

}