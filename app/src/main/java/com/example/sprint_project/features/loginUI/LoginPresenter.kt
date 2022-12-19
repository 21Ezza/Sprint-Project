package com.example.sprint_project.features.loginUI

import com.example.sprint_project.data.api.CredentialLoginApi
import com.example.sprint_project.data.api.UserApi
import com.example.sprint_project.data.network.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginPresenter (
    private val credentialApi: CredentialLoginApi,
    private val userApi: UserApi,
    private val uiContext: CoroutineContext = Dispatchers.Main
) {
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }
    private var view: LoginView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    private var isUsernameValid = true
    private var isPasswordValid = true

    fun onAttach(view: LoginView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun validateUser(userName: String): Boolean {
        val isUsernameValid = userName.length >= 5
        if (!isUsernameValid){
            view?.onError(1, "invalid username")
        } else {
            view?.onErrorUser()
        }
        return isUsernameValid
    }

    fun validatePassword(password: String): Boolean {
        val isPasswordValid = password.contains("[a-z]".toRegex())
                && password.contains("[A-Z]".toRegex())
                && password.contains("[0-9]".toRegex())
                && password.length >= 8
        if (!isPasswordValid){
            view?.onError(2,"invalid password")
        } else{
            view?.onErrorPassword()
        }
        return isPasswordValid
    }


    fun validateCredential(userName: String, password: String) {

        if (isPasswordValid && isUsernameValid){
            view?.onSuccessLogin(userName, password)
        }
        view?.onFinishedLoading()
    }

    fun register(email: String, password: String) {
        view?.onLoading()
        scope.launch {
            credentialApi
                .registerUser(email, password)
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessRegister()
                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                        else -> {}
                    }
                    view?.onFinishedLoading()
                }
        }
    }

    fun getUser() {
        view?.onLoading()
        scope.launch {
            userApi.getUser {
                when(it) {
                    is ResponseStatus.Success -> view?.onSuccessGetUser(it.data)
                    is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                    else -> {}
                }
                view?.onFinishedLoading()
            }
        }
    }

}