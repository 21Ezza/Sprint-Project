package com.example.sprint_project.features.register

import com.example.sprint_project.data.api.CredentialApi
import com.example.sprint_project.data.api.UserApi
import com.example.sprint_project.data.network.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisPresenter(
    private val credentialApi: CredentialApi,
    private val userApi: UserApi,
    private val uiContext: CoroutineContext = Dispatchers.Main
) {
    private var view: RegisView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    fun onAttach(view: RegisView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun validateEmail(email : String){
        val isEmailValid = email.contains("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())
        if(isEmailValid){
            view?.onErrorSuccess()
        } else {
            view?.onError(2,"Invalid Email")
        }
    }

    fun validatePassword(passwordOne : String, passwordTwo : String) {
        if(passwordOne.equals(passwordTwo)){
            view?.onErrorSuccess()
        } else{
            view?.onError(1,"Invalid Password")
        }
    }

    fun validateEmailAndPassword(email: String , password: String){
        view?.onLoading()
        if (email.isNotBlank() && password.isNotBlank()){
            view?.onSuccessLogin(email, password)
        }

    }

    fun register(email: String, password: String) {
        view?.onLoading()
        scope.launch {
            credentialApi
                .registerUser(email, password)
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessRegis()
                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                        else -> {}
                    }
                    view?.onFinishedLoading()
                }
        }
    }
}