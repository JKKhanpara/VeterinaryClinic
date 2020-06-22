package com.jenish.veterinaryclinic.data

import org.json.JSONObject

/**
 * Developed By JENISH KHANPARA on 22 June 2020.
 */
data class ConfigData(
    val isChatEnabled: Boolean,
    val isCallEnabled: Boolean,
    val workHours: String
)

/**
 * Convert a JSON object into a ConfigData
 */
fun JSONObject.asConfigData(): ConfigData = ConfigData(
    this.getBoolean("isChatEnabled"),
    this.getBoolean("isCallEnabled"),
    this.getString("workHours")
)
