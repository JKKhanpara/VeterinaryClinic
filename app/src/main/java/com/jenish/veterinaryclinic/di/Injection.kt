package com.jenish.veterinaryclinic.di

import com.jenish.veterinaryclinic.data.PetRepository
import com.jenish.veterinaryclinic.ui.pet.PetViewModelFactory
import okhttp3.OkHttpClient

/**
 * Developed By JENISH KHANPARA on 22 June 2020.
 */
object Injection {
    private val okHttpClient: OkHttpClient by lazy { OkHttpClient() }

    private val repository: PetRepository by lazy { PetRepository(okHttpClient) }

    val petViewModelFactory: PetViewModelFactory by lazy {
        PetViewModelFactory(repository)
    }
}