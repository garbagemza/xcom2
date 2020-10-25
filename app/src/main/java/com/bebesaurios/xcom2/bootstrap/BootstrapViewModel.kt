package com.bebesaurios.xcom2.bootstrap

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.bebesaurios.xcom2.util.exhaustive
import kotlinx.coroutines.*

class BootstrapViewModel : ViewModel() {
    private val configurationReplyAction = MutableLiveData<ConfigurationReply>()

    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.CheckData -> viewModelScope.launch { checkData() }
        }.exhaustive
    }

    @WorkerThread
    private suspend fun checkData() = withContext(Dispatchers.IO) {
        val requiredLocalKeys = listOf("master", "index")
        ConfigurationManager.updateConfigurations(requiredLocalKeys, configurationReplyAction)
    }

    fun configurationReply() : LiveData<ConfigurationReply> = configurationReplyAction
}

sealed class InputAction {
    object CheckData : InputAction()
}