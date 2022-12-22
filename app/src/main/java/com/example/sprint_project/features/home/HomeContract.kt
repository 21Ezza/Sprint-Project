package com.example.sprint_project.features.home

import com.example.sprint_project.data.model.ListResource
import com.example.sprint_project.data.model.User

interface HomeContract {
    fun onLoading()
    fun onFinishedLoading()
    fun onErrorUserList(message: String)
    fun onSuccesGetUserList(users: List<User>) {}
    fun onErrorListResource(message: String)
    fun onSuccessListResource(list: List<ListResource>) {}
}