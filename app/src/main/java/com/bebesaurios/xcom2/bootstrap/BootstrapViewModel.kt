package com.bebesaurios.xcom2.bootstrap

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.database.Repository
import com.bebesaurios.xcom2.database.entities.ConfigurationEntity
import com.bebesaurios.xcom2.service.MSIService
import com.bebesaurios.xcom2.service.PageService
import com.bebesaurios.xcom2.service.Result
import com.bebesaurios.xcom2.util.SingleLiveEvent
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.postMainThread
import kotlinx.coroutines.*
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BootstrapViewModel : ViewModel() {
    private val repository: Repository by inject(Repository::class.java)
    private val replyAction = SingleLiveEvent<ReplyAction>()

    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.CheckData -> viewModelScope.launch { checkData() }
        }.exhaustive
    }

    @WorkerThread
    private suspend fun checkData() = withContext(Dispatchers.IO) {
        withContext(Dispatchers.Main) { updateWorkStatus(R.string.checking_updates) }

        val requiredLocalKeys = listOf("master", "index")

        val localDeferred = requiredLocalKeys.map { async { getLocalConfiguration(it) } }
        val localConfigs = localDeferred.awaitAll()

        val requiredConfigurations = localConfigs.filter { it.config == null }
        val existingConfigurations = localConfigs.filter { it.config != null }

        val deferredRequiredConfigurations = buildDeferredRequiredConfigurations(requiredConfigurations)
        val requiredConfigurationResults = deferredRequiredConfigurations.awaitAll()

        val filteredRequired = requiredConfigurationResults.filter { it.json == null }

        if (filteredRequired.isNotEmpty()) {
            withContext(Dispatchers.Main) { updateWorkStatus(R.string.unable_to_download_content_try_again_later) }
            return@withContext
        }

        // at this point all required data is downloaded, proceed to save
        proceedSavingNewConfigurations(requiredConfigurationResults)

        val deferredExistingConfigurationsMetadata = existingConfigurations.map { async { downloadMetadata(it.key) } }
        val existingConfigurationsMetadata = deferredExistingConfigurationsMetadata.awaitAll().filter { it.json != null }

        val updates = buildUpdates(existingConfigurations, existingConfigurationsMetadata)
        val staleUpdates = updates.filter { it.existingToken != it.newToken }

        val deferredStaleUpdates = staleUpdates.map { async { download(it.key) } }
        val staleUpdateResults = deferredStaleUpdates.awaitAll().filter { it.json != null }

        proceedSavingExistingConfigurations(staleUpdateResults, updates)

        withContext(Dispatchers.Main) { moveToHomeScreen() }
    }

    private fun proceedSavingNewConfigurations(configurationResults: List<DownloadContext>) {
        val groups = configurationResults.groupBy { it.key }
        groups.entries.forEach { entry ->
            val key = entry.key
            val data = entry.value.associateBy { it.type }
            val file = data.getValue(DownloadType.File)
            val metadata = data.getValue(DownloadType.Metadata)
            val newToken = metadata.json?.getString("updated")!!

            repository.saveConfiguration(
                key,
                json = file.json!!,
                newToken = newToken
            )
        }
    }

    private fun proceedSavingExistingConfigurations(staleUpdateResults: List<DownloadContext>, updates: List<UpdateContext>) {
        val lookup = updates.associateBy { it.key }
        staleUpdateResults.forEach {
            val key = it.key
            val json = it.json!!
            val updateContext = lookup.getValue(key)
            val newToken = updateContext.newToken
            repository.saveConfiguration(key, json, newToken)
        }
    }

    private fun buildUpdates(configurations: List<Configuration>, metas: List<DownloadContext>): List<UpdateContext> {
        val list = mutableListOf<UpdateContext>()
        val lookup = metas.associateBy { it.key }

        configurations.forEach { entry ->
            val key = entry.key
            val config = entry.config!!

            val json = lookup.getValue(key).json!!
            val newToken = json.getString("updated")
            list.add(UpdateContext(key, newToken, config.token))
        }
        return list
    }

    private fun CoroutineScope.buildDeferredRequiredConfigurations(configurations: List<Configuration>): List<Deferred<DownloadContext>> {
        val list = mutableListOf<Deferred<DownloadContext>>()
        repeat(configurations.size) { i->
            val key = configurations[i].key
            val deferredMetadata = async { downloadMetadata(key) }
            val deferred = async { download(key) }

            list.add(deferredMetadata)
            list.add(deferred)
        }
        return list
    }

    private suspend fun getLocalConfiguration(key: String) : Configuration = suspendCoroutine {
        val config = Configuration(key, repository.getConfiguration(key))
        it.resume(config)
    }

    private suspend fun download(key: String) : DownloadContext = suspendCoroutine { continuation ->
        when (key) {
            "master" -> {
                val result = MSIService.downloadMSI()
                when (result) {
                    is Result.Success -> continuation.resume(DownloadContext(key, DownloadType.File, result.value))
                    is Result.Failure -> continuation.resume(DownloadContext(key, DownloadType.File, null))
                }.exhaustive
            }
            else -> {
                val result = PageService.downloadPage(key)
                when (result) {
                    is Result.Success -> continuation.resume(DownloadContext(key, DownloadType.File, result.value))
                    is Result.Failure -> continuation.resume(DownloadContext(key, DownloadType.File, null))
                }.exhaustive
            }
        }
    }

    private suspend fun downloadMetadata(key: String) : DownloadContext = suspendCoroutine{ continuation ->
        when (key) {
            "master" -> {
                val result = MSIService.getMSIMetadata()
                when (result) {
                    is Result.Success -> continuation.resume(DownloadContext(key, DownloadType.Metadata, result.value))
                    is Result.Failure -> continuation.resume(DownloadContext(key, DownloadType.Metadata, null))
                }.exhaustive
            }
            else -> {
                val result = PageService.getPageMetadata(key)
                when (result) {
                    is Result.Success -> continuation.resume(DownloadContext(key, DownloadType.Metadata, result.value))
                    is Result.Failure -> continuation.resume(DownloadContext(key, DownloadType.Metadata,null))
                }.exhaustive
            }
        }
    }

    @MainThread
    private fun updateWorkStatus(@StringRes resource: Int) {
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

data class Configuration(val key: String, val config: ConfigurationEntity?)

enum class DownloadType {
    File,
    Metadata
}

data class DownloadContext(val key: String, val type: DownloadType, val json: JSONObject?)
data class UpdateContext(val key: String, val newToken: String, val existingToken: String)
