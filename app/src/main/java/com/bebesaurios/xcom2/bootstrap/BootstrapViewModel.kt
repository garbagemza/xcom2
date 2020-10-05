package com.bebesaurios.xcom2.bootstrap

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.database.DatabaseFeeder
import com.bebesaurios.xcom2.database.DatabaseModel
import com.bebesaurios.xcom2.service.MSIMetadataResult
import com.bebesaurios.xcom2.service.MSIResult
import com.bebesaurios.xcom2.service.downloadMSI
import com.bebesaurios.xcom2.service.getMSIMetadata
import com.bebesaurios.xcom2.util.Preferences
import com.bebesaurios.xcom2.util.SingleLiveEvent
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.postMainThread
import org.json.JSONObject
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
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
    private fun downloadMSIForFirstTime(newToken: String) {
        updateWorkStatus(R.string.downloading_new_content)
        when (val result = downloadMSI()) {
            is MSIResult.Success -> updateMSI(newToken, result.json)
            MSIResult.Failure -> updateWorkStatus(R.string.unable_to_download_content_try_again_later)
        }.exhaustive
    }

    @WorkerThread
    private fun downloadMSI(newToken: String) {
        updateWorkStatus(R.string.updating_content)
        when (val result = downloadMSI()) {
            is MSIResult.Success -> updateMSI(newToken, result.json)
            MSIResult.Failure -> updateWorkStatus(R.string.unable_to_download_content_try_again_later)
        }.exhaustive
    }

    private fun updateMSI(newToken: String, json: JSONObject) {
        val model = DatabaseModel(json)
        val feeder: DatabaseFeeder by inject(DatabaseFeeder::class.java) { parametersOf(model) }
        feeder.start()
        val preferences: Preferences by inject(Preferences::class.java)
        preferences.setMSIToken(newToken)
        moveToHomeScreen()
    }


    @WorkerThread
    private fun updateWorkStatus(@StringRes resource: Int) = postMainThread {
        replyAction.value = ReplyAction.UpdateWorkStatus(resource)
    }

    @WorkerThread
    private fun moveToHomeScreen() = postMainThread {
        replyAction.value = ReplyAction.GoToHome
    }

    fun reply() : LiveData<ReplyAction> = replyAction
}

sealed class InputAction {
    object CheckData : InputAction()
}

sealed class ReplyAction {
    data class UpdateWorkStatus(@StringRes val stringRes: Int) : ReplyAction()
    object GoToHome : ReplyAction()
}