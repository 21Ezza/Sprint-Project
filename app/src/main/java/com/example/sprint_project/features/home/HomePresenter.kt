package com.example.sprint_project.features.home

import com.example.sprint_project.data.api.UserApi
import com.example.sprint_project.data.network.ResponseStatus
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomePresenter(
    private val view: HomeContract,
    private val api: UserApi,
    uiContext: CoroutineContext = Dispatchers.Main) {

    private val supervisorJob: Job = SupervisorJob()
    private val scope = CoroutineScope(supervisorJob + uiContext)

    fun onAttach(view: HomeContract) {
        getUsers()
        api.getError {
            scope.launch {
                when (it) {
                    is ResponseStatus.Failed -> view.onErrorUserList(it.message)
                    else -> {}
                }
            }
        }
    }

    fun onDetach(){
        scope.cancel()
    }

    fun getUsers(page: Int = 1) {
        view.onLoading()
        api.getUserPagination {
            scope.launch {
                when (it) {
                    is ResponseStatus.Success -> view.onSuccesGetUserList(it.data.toMutableList())
                    is ResponseStatus.Failed -> view.onErrorUserList(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }
}