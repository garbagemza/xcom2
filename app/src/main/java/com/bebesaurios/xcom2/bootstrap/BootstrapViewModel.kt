package com.bebesaurios.xcom2.bootstrap

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.service.MSIMetadataResult
import com.bebesaurios.xcom2.service.MSIResult
import com.bebesaurios.xcom2.service.downloadMSI
import com.bebesaurios.xcom2.service.getMSIMetadata
import com.bebesaurios.xcom2.util.SingleLiveEvent
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.postMainThread
import org.json.JSONObject
import kotlin.concurrent.thread

class BootstrapViewModel : ViewModel() {

    private val replyAction = SingleLiveEvent<ReplyAction>()

    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.CheckData -> checkData()
        }.exhaustive
    }

    private fun checkData() {
        thread {
            when (val result = getMSIMetadata()) {
                MSIMetadataResult.NoUpdate -> moveToHomeScreen()
                is MSIMetadataResult.TokenNeedsUpdate -> {
                    if (result.currentToken.isEmpty())
                        downloadMSIForFirstTime(result.newToken)
                    else
                        downloadMSI(result.newToken)
                }
            }.exhaustive
        }
    }

    @WorkerThread
    private fun downloadMSI(newToken: String) {
        TODO("Not yet implemented")
    }

    @WorkerThread
    private fun downloadMSIForFirstTime(newToken: String) {
        updateWorkStatus(R.string.downloading_new_content)
        when (val result = downloadMSI()) {
            is MSIResult.Success -> updateMSI(newToken, result.json)
            MSIResult.Failure -> updateWorkStatus(R.string.unable_to_download_content_try_again_later)
        }.exhaustive
    }


    private fun updateMSI(newToken: String, json: JSONObject) {
        TODO("Not yet implemented")
    }


    @WorkerThread
    private fun updateWorkStatus(@StringRes resource: Int) = postMainThread {
        replyAction.value = ReplyAction.UpdateWorkStatus(resource)
    }

    @WorkerThread
    private fun moveToHomeScreen() = postMainThread {
        replyAction.value = ReplyAction.GoToHome
    }

}

sealed class InputAction {
    object CheckData : InputAction()
}

sealed class ReplyAction {
    data class UpdateWorkStatus(@StringRes val stringRes: Int) : ReplyAction()
    object GoToHome : ReplyAction()
}