package com.jenish.veterinaryclinic.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jenish.veterinaryclinic.testing.OpenForTesting
import com.jenish.veterinaryclinic.ui.pet.Pet
import com.jenish.veterinaryclinic.ui.pet.asPetList
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * Developed By JENISH KHANPARA on 22 June 2020.
 */
@OpenForTesting
class PetRepository(private val okHttpClient: OkHttpClient) {

    fun getConfig(): LiveData<Resource<ConfigData>> {
        val configData = MutableLiveData<Resource<ConfigData>>()

        val request: Request = Request.Builder()
            .url("https://raw.githubusercontent.com/JKKhanpara/JSONRepo/master/config.json")
            .build()

        configData.value = Resource.loading(null)

        okHttpClient.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseObject = JSONObject(response.body!!.string())
                        configData.postValue(
                            Resource.success(
                                responseObject.getJSONObject("settings").asConfigData()
                            )
                        )
                    } else {
                        configData.postValue(Resource.error("", null))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    configData.postValue(Resource.error("", null))
                }
            })
        return configData
    }

    fun getPets(): LiveData<Resource<List<Pet>>> {
        val pets = MutableLiveData<Resource<List<Pet>>>()

        val request: Request = Request.Builder()
            .url("https://raw.githubusercontent.com/JKKhanpara/JSONRepo/master/pets.json")
            .build()

        pets.value = Resource.loading(null)
        okHttpClient.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseObject = JSONObject(response.body!!.string())
                        val petsJsonArray = responseObject.getJSONArray("pets")
                        pets.postValue(Resource.success(petsJsonArray.asPetList()))
                    } else {
                        pets.postValue(Resource.error("", null))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    pets.postValue(Resource.error("", null))
                }
            })
        return pets
    }
}
