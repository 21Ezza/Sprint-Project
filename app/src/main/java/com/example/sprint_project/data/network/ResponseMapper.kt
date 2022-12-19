package com.example.sprint_project.data.network

import okhttp3.Response
import org.json.JSONObject

fun mapFailedResponse(response: Response): ResponseStatus.Failed {
    val jsonBody = response.body?.string().let { if(!it.isNullOrEmpty()) JSONObject(it) else JSONObject() }
    return ResponseStatus.Failed(response.code, jsonBody.optString("message", ""))
}