package br.com.cwisicredi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.cwisicredi.repository.CheckInRepository
import br.com.cwisicredi.repository.EventRepository
import br.com.network.exception.NetworkDataException
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @Mock
    private lateinit var checkInRepository: CheckInRepository

    @Mock
    private lateinit var eventRepository: EventRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun before() {
        detailViewModel = DetailViewModel(eventRepository, checkInRepository)
    }

    @Test
    fun validateRequiredInfoAndNotifyErrorWithEmptyName() {
        assertEquals(false, detailViewModel.validateRequiredInfoAndNotifyError("", "teste"))
        assertEquals("check if valid name called", true, detailViewModel.invalidName.value)
        assertEquals("check if invalid email called", null, detailViewModel.invalidEmail.value)
    }

    @Test
    fun validateRequiredInfoAndNotifyErrorWithEmptyEmail() {
        assertEquals(false, detailViewModel.validateRequiredInfoAndNotifyError("name", ""))
        assertEquals("check if invalid name called", null, detailViewModel.invalidName.value)
        assertEquals("check if valid email called", true, detailViewModel.invalidEmail.value)
    }

    @Test
    fun validateRequiredInfoAndNotifyErrorWithEmptyEmailAndName() {
        assertEquals(false, detailViewModel.validateRequiredInfoAndNotifyError("", ""))
        assertEquals("check if empty name called", true, detailViewModel.invalidName.value)
        assertEquals("check if empty email called", true, detailViewModel.invalidEmail.value)
    }

    @Test
    fun validateRequiredInfoAndNotifyErrorWithEmailAndName() {
        assertEquals(true, detailViewModel.validateRequiredInfoAndNotifyError("teste", "teste"))
        assertEquals("check if name ", null, detailViewModel.invalidName.value)
        assertEquals("check if email", null, detailViewModel.invalidEmail.value)
    }

    @Test
    fun checkInSuccess() = runBlocking {
        val response = ResponseBody.create(null, "")
        Mockito.lenient().`when`(checkInRepository.postCheckIn(any())).thenReturn(async { response })
        detailViewModel.checkIn(0, "", "").observeForever {
            assertEquals(response, it)
        }
    }

    @Test
    fun checkInWithNetworkError() = runBlocking {
        val error = NetworkDataException(null)
        Mockito.lenient().`when`(checkInRepository.postCheckIn(any())).thenAnswer { throw error }
        detailViewModel.checkIn(0, "", "").observeForever {
            assertEquals(error, it)
        }
    }

}