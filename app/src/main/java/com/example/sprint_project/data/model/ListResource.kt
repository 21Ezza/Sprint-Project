package com.example.sprint_project.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ListResource(
    val id: Int,
    val name: String,
    val year: String,
    val color: String,
    @Json(name = "pantone_value")
    val pantoneValue: String
)