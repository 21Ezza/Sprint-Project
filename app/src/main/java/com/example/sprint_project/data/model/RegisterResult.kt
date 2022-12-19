package com.example.sprint_project.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResult(
    val id: Int? = null,
    val token: String? = null,
)
