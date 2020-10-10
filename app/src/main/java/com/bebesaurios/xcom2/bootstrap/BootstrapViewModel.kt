package com.bebesaurios.xcom2.bootstrap

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.database.DatabaseFeeder
import com.bebesaurios.xcom2.database.DatabaseModel
import com.bebesaurios.xcom2.service.MSIService
import com.bebesaurios.xcom2.service.Result
import com.bebesaurios.xcom2.util.Preferences
import com.bebesaurios.xcom2.util.SingleLiveEvent
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.postMainThread
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import java.util.*
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
            updateWorkStatus(R.string.checking_updates)
            getMetadata { result ->
                when (result) {
                    is Result.Success -> successDownloadingMetadata(result.value)
                    is Result.Failure -> failedDownloadProcess()
                }.exhaustive
            }
        }
    }

    private fun getMetadata(block: (result: Result<JSONObject, Exception>) -> Unit) {
        val result = MSIService.getMSIMetadata()
        block.invoke(result)
    }

    private fun successDownloadingMetadata(value: JSONObject) {
        val newToken = getTokenFromMSIMetadata(value)
        if (newToken.isNotEmpty()) {
            successGettingNewToken(newToken)
        } else
            failedDownloadProcess()
    }

    private fun successGettingNewToken(newToken: String) {
        val preferences: Preferences by inject(Preferences::class.java)
        val currentToken = preferences.getMSIToken()
        if (currentToken != newToken) {
            tokenNeedsUpdate(currentToken, newToken)
        } else
            moveToHomeScreen()
    }

    // retrieves the update token from msi metadata, returns empty string if error
    private fun getTokenFromMSIMetadata(value: JSONObject) : String {
        try {
            return value.getString("updated")
        } catch (e: JSONException) {

        }
        return ""
    }

    @WorkerThread
    private fun tokenNeedsUpdate(currentToken: String, newToken: String) {
        updateWorkStatus(if (currentToken.isEmpty()) R.string.downloading_new_content else R.string.updating_content)
        downloadMSI { result ->
            when (result) {
                is Result.Success -> successDownloadMSI(newToken, result.value)
                is Result.Failure -> failedDownloadProcess()
            }.exhaustive
        }
    }

    private fun downloadMSI(block: (Result<JSONObject, Exception>) -> Unit) {
        val result = MSIService.downloadMSI()
        block.invoke(result)
    }

    private fun successDownloadMSI(newToken: String, json: JSONObject) {
        updateWorkStatus(R.string.downloading_index)

        val model = DatabaseModel(json)

        val article = model.articles.firstOrNull { it.key == "index" }
        val articleTranslation = model.translations.firstOrNull { it.key == "index" && it.locale == Locale.getDefault().toString() }

        val indexFilename = when {
            articleTranslation != null -> articleTranslation.contentFile
            article != null -> article.contentFile
            else -> "index.json"
        }

        downloadIndex(indexFilename) { result ->
            when (result) {
                is Result.Success -> updateMSI(newToken, model, result.value)
                is Result.Failure -> failedDownloadProcess()
            }
        }
    }

    private fun downloadIndex(filename: String, block: (Result<JSONObject, Exception>) -> Unit) {
        val result = MSIService.downloadPage(filename)
        block.invoke(result)
    }

    private fun failedDownloadProcess() {
        val preferences: Preferences by inject(Preferences::class.java)
        val currentToken = preferences.getMSIToken()
        if (currentToken.isEmpty())
            updateWorkStatus(R.string.unable_to_download_content_try_again_later)
        else
            moveToHomeScreen()
    }

    private fun updateMSI(newToken: String, model: DatabaseModel, indexContent: JSONObject) {
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

data class IndexInfo()
sealed class InputAction {
    object CheckData : InputAction()
}

sealed class ReplyAction {
    data class UpdateWorkStatus(@StringRes val stringRes: Int) : ReplyAction()
    object GoToHome : ReplyAction()
}