package com.jenish.veterinaryclinic.ui.pet

import org.json.JSONArray
import org.json.JSONObject

/**
 * Developed By JENISH KHANPARA on 19 June 2020.
 */
data class Pet(
    val title: String,
    val imageUrl: String,
    val contentUrl: String,
    val dateAdded: String
)


/**
 * Converts the JSONArray to List
 */
fun JSONArray.asPetList(): List<Pet> {
    val list = ArrayList<Pet>()
    for (i in 0 until this.length()) {
        val petObject: JSONObject = this.getJSONObject(i)
        list.add(
            Pet(
                petObject.getString("title"),
                petObject.getString("image_url"),
                petObject.getString("content_url"),
                petObject.getString("date_added")
            )
        )
    }
    return list
}