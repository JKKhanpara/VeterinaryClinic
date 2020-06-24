package com.jenish.veterinaryclinic.ui.pet

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jenish.veterinaryclinic.data.ConfigData
import com.jenish.veterinaryclinic.data.PetRepository
import com.jenish.veterinaryclinic.data.Resource
import com.jenish.veterinaryclinic.testing.OpenForTesting
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*


/**
 * Developed By JENISH KHANPARA on 18 June 2020.
 */
@OpenForTesting
class PetViewModel(petRepository: PetRepository) : ViewModel() {

    val configData: LiveData<Resource<ConfigData>> = petRepository.getConfig()

    val isChatEnabled: LiveData<Boolean> =
        Transformations.map(configData) { configData ->
            configData.status == Resource.Status.SUCCESS && configData.data?.isChatEnabled ?: false
        }

    val isCallEnabled: LiveData<Boolean> =
        Transformations.map(configData) { configData ->
            configData.status == Resource.Status.SUCCESS && configData.data?.isCallEnabled ?: false
        }

    val workHours: LiveData<String> =
        Transformations.map(configData) { configData ->
            configData.data?.workHours ?: ""
        }

    var pets: LiveData<Resource<List<Pet>>> = petRepository.getPets()

    /**
     * Check if the current time is within working hours
     *
     * @return true current time is within working hours, false otherwise.
     */
    fun isWorkingHours(): Boolean {
        val delimiter = " "
        val parts = workHours.value?.split(delimiter)
        parts?.let {
            if (it.size > 3) {
                val startTime = it[1]
                val endTime = it[3]
                return !isWeekend() && isNowTimeBetween(startTime, endTime)
            }
        }
        return false
    }


    /**
     * Returns whether current time lies between two times.
     *
     * @return true current time lies between two times, false otherwise.
     */
    private fun isNowTimeBetween(startTime: String?, endTime: String?): Boolean {
        try {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("H:m", Locale.US)
            val start = LocalTime.parse(startTime, formatter)
            val end = LocalTime.parse(endTime, formatter)
            val now = LocalTime.now()
            if (start.isBefore(end)) return now.isAfter(start) && now.isBefore(end)
            return if (now.isBefore(start)) now.isBefore(start) && now.isBefore(end) else now.isAfter(
                start
            ) && now.isAfter(end)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false

    }

    /**
     * Returns whether current date is the weekend
     *
     * @return true if current date is the weekend, false otherwise.
     */
    private fun isWeekend(): Boolean {
        val date: LocalDate = LocalDate.now()
        val day: DayOfWeek = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK))
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY
    }
}

