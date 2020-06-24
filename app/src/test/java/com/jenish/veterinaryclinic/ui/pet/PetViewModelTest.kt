package com.jenish.veterinaryclinic.ui.pet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jenish.veterinaryclinic.data.ConfigData
import com.jenish.veterinaryclinic.data.PetRepository
import com.jenish.veterinaryclinic.data.Resource
import com.jenish.veterinaryclinic.util.ApiUtil
import com.jenish.veterinaryclinic.util.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*


/**
 * Developed By JENISH KHANPARA on 24 June 2020.
 */
@RunWith(JUnit4::class)
class PetViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mock<PetRepository>()
    private lateinit var petViewModel: PetViewModel

    @Test
    fun configDataWithChatEnabledCallEnabled() {
        // Mock API response
        val configData = ConfigData(true, isCallEnabled = true, workHours = "M-F 09:00 - 18:00")
        `when`(mockRepository.getConfig()).thenReturn(ApiUtil.successCall(configData))

        // init ViewModel
        petViewModel = PetViewModel(mockRepository)

        // config data
        val observer = mock<Observer<Resource<ConfigData>>>()
        petViewModel.configData.observeForever(observer)
        verify(mockRepository, times(1)).getConfig()
        verify(observer).onChanged(Resource.success(configData))

        // chat enabled
        val observerChatEnabled = mock<Observer<Boolean>>()
        petViewModel.isChatEnabled.observeForever(observerChatEnabled)
        verify(observerChatEnabled).onChanged(true)

        // call enabled
        val observerCallEnabled = mock<Observer<Boolean>>()
        petViewModel.isCallEnabled.observeForever(observerCallEnabled)
        verify(observerCallEnabled).onChanged(true)

        petViewModel.configData.removeObserver(observer)
    }

    @Test
    fun configDataWithChatEnabledCallDisabled() {
        // Mock API response
        val configData = ConfigData(true, isCallEnabled = false, workHours = "M-F 09:00 - 18:00")
        `when`(mockRepository.getConfig()).thenReturn(ApiUtil.successCall(configData))

        // init ViewModel
        petViewModel = PetViewModel(mockRepository)

        // observer
        val observer = mock<Observer<Resource<ConfigData>>>()
        petViewModel.configData.observeForever(observer)
        verify(mockRepository, times(1)).getConfig()
        verify(observer).onChanged(Resource.success(configData))

        // chat enabled
        val observerChatEnabled = mock<Observer<Boolean>>()
        petViewModel.isChatEnabled.observeForever(observerChatEnabled)
        verify(observerChatEnabled).onChanged(true)

        // call disabled
        val observerCallEnabled = mock<Observer<Boolean>>()
        petViewModel.isCallEnabled.observeForever(observerCallEnabled)
        verify(observerCallEnabled).onChanged(false)

        petViewModel.configData.removeObserver(observer)
    }

    @Test
    fun configDataWithChatDisabledCallDisabled() {
        // Mock API response
        val configData = ConfigData(false, isCallEnabled = false, workHours = "M-F 09:00 - 18:00")
        `when`(mockRepository.getConfig()).thenReturn(ApiUtil.successCall(configData))

        // init ViewModel
        petViewModel = PetViewModel(mockRepository)

        // observer
        val observer = mock<Observer<Resource<ConfigData>>>()
        petViewModel.configData.observeForever(observer)
        verify(mockRepository, times(1)).getConfig()
        verify(observer).onChanged(Resource.success(configData))

        // chat disabled
        val observerChatEnabled = mock<Observer<Boolean>>()
        petViewModel.isChatEnabled.observeForever(observerChatEnabled)
        verify(observerChatEnabled).onChanged(false)

        // call disabled
        val observerCallEnabled = mock<Observer<Boolean>>()
        petViewModel.isCallEnabled.observeForever(observerCallEnabled)
        verify(observerCallEnabled).onChanged(false)

        petViewModel.configData.removeObserver(observer)
    }

    @Test
    fun configDataWithChatDisabledCallEnabled() {
        // Mock API response
        val configData = ConfigData(false, isCallEnabled = true, workHours = "M-F 09:00 - 18:00")
        `when`(mockRepository.getConfig()).thenReturn(ApiUtil.successCall(configData))

        // init ViewModel
        petViewModel = PetViewModel(mockRepository)

        // observer
        val observer = mock<Observer<Resource<ConfigData>>>()
        petViewModel.configData.observeForever(observer)
        verify(mockRepository, times(1)).getConfig()
        verify(observer).onChanged(Resource.success(configData))

        // chat disabled
        val observerChatEnabled = mock<Observer<Boolean>>()
        petViewModel.isChatEnabled.observeForever(observerChatEnabled)
        verify(observerChatEnabled).onChanged(false)

        // call enabled
        val observerCallEnabled = mock<Observer<Boolean>>()
        petViewModel.isCallEnabled.observeForever(observerCallEnabled)
        verify(observerCallEnabled).onChanged(true)

        petViewModel.configData.removeObserver(observer)
    }

    @Test
    fun pets() {

        `when`(mockRepository.getPets()).thenReturn(ApiUtil.successCall(emptyList()))

        // init ViewModel
        petViewModel = PetViewModel(mockRepository)

        // observer
        val observer = mock<Observer<Resource<List<Pet>>>>()
        petViewModel.pets.observeForever(observer)

        verify(mockRepository, times(1)).getPets()

        petViewModel.pets.removeObserver(observer)
    }
}
