package com.example.sprint_project.features.register

interface RegisView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int,message: String)
    fun onSuccessLogin(username: String, password: String)
    fun onSuccessRegis()
    fun onErrorSuccess()

}