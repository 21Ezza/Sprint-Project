package com.example.sprint_project.data.api

import com.example.sprint_project.data.model.ListPageResource
import com.example.sprint_project.data.model.ListResource
import com.example.sprint_project.data.model.User
import com.example.sprint_project.data.model.UserPagination
import com.example.sprint_project.data.network.NetworkClient
import com.example.sprint_project.data.network.ResponseStatus
import com.example.sprint_project.data.network.deserializeJson
import com.example.sprint_project.data.network.mapFailedResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class ListResourceApi {
    private val usersEndpoint = "/unknown"
    fun getListResource(onResponse: (ResponseStatus<ListPageResource>) -> Unit) {
        NetworkClient
            .client
            .newCall(
                NetworkClient.requestBuilder(usersEndpoint)
            )
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
//                        val body = JSONObject(response.body?.string() ?: "")
                        val data = deserializeJson<ListPageResource>(response.body?.string() ?: "")
                        data?.let {
                            onResponse.invoke(
                                ResponseStatus.Success(data)
                            )
                        }
                    } else {
                        onResponse.invoke(
                            mapFailedResponse(response)
                        )
                    }
                }
            })
    }
    fun getError(onResponse: (ResponseStatus<Nothing>) -> Unit) {
        NetworkClient
            .client
            .newCall(NetworkClient.requestBuilder("/unknown/23"))
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(ResponseStatus.Failed(-1, e.message.toString(), e))
                }

                override fun onResponse(call: Call, response: Response) {
                    onResponse.invoke(ResponseStatus.Failed(-1, response.message))
                    response.body?.close()
                }
            })
    }

    fun getListResourcePage(pages: Int = 1, onResponse: (ResponseStatus<List<ListResource>>) -> Unit) {
        val endpoint = "$usersEndpoint${if (pages > 1) "?page=$pages" else ""}"
        val request = NetworkClient.requestBuilder(endpoint)
        NetworkClient
            .client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(
                        ResponseStatus.Failed(
                            code = -1,
                            message = e.message.toString(),
                            throwable = e
                        )
                    )
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val userPagination = deserializeJson<UserPagination>(response.body?.string() ?: "") ?: UserPagination()
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = userPagination.data,
                                method = "GET",
                                status = true
                            )
                        )
                    } else {
                        onResponse.invoke(
                            mapFailedResponse(response)
                        )
                    }
                    response.body?.close()
                }
            })
    }
}