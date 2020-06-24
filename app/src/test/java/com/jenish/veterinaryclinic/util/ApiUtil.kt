package com.jenish.veterinaryclinic.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jenish.veterinaryclinic.data.Resource

/**
 * Developed By JENISH KHANPARA on 24 June 2020.
 */
object ApiUtil {
    fun <T : Any> successCall(data: T) = MutableLiveData<Resource<T>>().apply {
        value = Resource.success(data)
    } as LiveData<Resource<T>>
}