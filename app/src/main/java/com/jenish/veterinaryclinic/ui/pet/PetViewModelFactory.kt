package com.jenish.veterinaryclinic.ui.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jenish.veterinaryclinic.data.PetRepository

/**
 * Developed By JENISH KHANPARA on 22 June 2020.
 */
class PetViewModelFactory constructor(private val repository: PetRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            PetViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}