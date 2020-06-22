package com.assignmentmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignmentmvvm.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel :ViewModel(){

    private val mainRepository = MainRepository()

    val usersSuccessLiveData = mainRepository.usersSuccessLiveData
    val usersFailureLiveData = mainRepository.usersFailureLiveData

    fun getTodos() {
        //this is coroutine viewmodel
         //mainRepository.getCoroutineData()

        //this is coroutine viewmodel scope to call suspend fun of repo.
        // ViewModel includes a built-in viewModelScope.
        // This provides a standard way to launch coroutines within the scope of the ViewModel

        viewModelScope.launch { mainRepository.getCoroutineData() }
    }

}