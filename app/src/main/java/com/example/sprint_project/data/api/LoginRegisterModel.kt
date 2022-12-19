package com.example.sprint_project.data.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRegisterModel(
    val email: String?,
    val password: String?
)