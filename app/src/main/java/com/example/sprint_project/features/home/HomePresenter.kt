package com.example.sprint_project.features.home

import com.example.sprint_project.data.api.ListResourceApi
import com.example.sprint_project.data.api.UserApi
import com.example.sprint_project.data.network.ResponseStatus
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomePresenter(
    private val view: HomeContract,
    private val api: UserApi,
    private val listApi: ListResourceApi,
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
        getList()
        listApi.getError {
            scope.launch {
                when (it) {
                    is ResponseStatus.Failed -> view.onErrorListResource(it.message)
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
    fun getList(page: Int = 1) {
        view.onLoading()
        listApi.getListResourcePage {
            scope.launch {
                when (it) {
                    is ResponseStatus.Success -> view.onSuccessListResource(it.data)
                    is ResponseStatus.Failed -> view.onErrorListResource(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }
}