package com.assignmentmvvm.repository

import androidx.lifecycle.MutableLiveData
import com.retrofit.model.TodoDataModel
import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainRepository {

    val usersSuccessLiveData = MutableLiveData<MutableList<TodoDataModel>>()
    val usersFailureLiveData = MutableLiveData<Boolean>()


       suspend fun getCoroutineData() {

        val service = ApiClient.getRetrofit()
        //CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTodos()
        //    withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        usersSuccessLiveData.postValue(response.body())

                    } else {
                        usersFailureLiveData.postValue(true)
                    }
                } catch (e: HttpException) {
                    usersFailureLiveData.postValue(true)
                } catch (e: Throwable) {
                    usersFailureLiveData.postValue(true)
                }
         //   }
       // }

    }
}