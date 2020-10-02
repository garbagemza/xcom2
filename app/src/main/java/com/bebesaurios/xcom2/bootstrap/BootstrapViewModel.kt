package com.bebesaurios.xcom2.bootstrap

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.bebesaurios.xcom2.service.MSIMetadataResult
import com.bebesaurios.xcom2.service.getMSIMetadata
import com.bebesaurios.xcom2.util.exhaustive
import kotlin.concurrent.thread

class BootstrapViewModel : ViewModel() {


    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.CheckData -> checkData()
        }.exhaustive
    }

    private fun checkData() {
        thread {
            when (getMSIMetadata()) {
                MSIMetadataResult.NoUpdate -> {} // OK! we won't update the master search
                MSIMetadataResult.TokenDoesNotExist -> {}
                is MSIMetadataResult.TokenNeedsUpdate -> {}
            }.exhaustive
        }
    }

}

sealed class InputAction {
    object CheckData : InputAction()
}