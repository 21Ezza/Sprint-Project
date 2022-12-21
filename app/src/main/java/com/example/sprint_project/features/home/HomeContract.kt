package com.example.sprint_project.features.home

import com.example.sprint_project.data.model.ListPageResource
import com.example.sprint_project.data.model.ListResource

interface HomeContract {
    fun onLoading()
    fun onFinishedLoading()
    fun onErrorListResource(message: String)
    fun onSuccessListResource(list: List<ListResource>) {}
}