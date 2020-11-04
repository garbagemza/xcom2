package com.bebesaurios.xcom2.main.page

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bebesaurios.xcom2.bootstrap.ConfigurationManager
import com.bebesaurios.xcom2.bootstrap.ConfigurationReply
import com.bebesaurios.xcom2.database.Repository
import com.bebesaurios.xcom2.main.page.model.*
import com.bebesaurios.xcom2.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject

class PageViewModel : ViewModel() {
    private val replyAction = MutableLiveData<ReplyAction>()
    private val configurationAction = MutableLiveData<ConfigurationReply>()

    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.ShowIndex -> viewModelScope.launch { withContext(Dispatchers.Main) { showIndex() } }
            is InputAction.BuildPage -> viewModelScope.launch { buildPage(action.articleKey) }
            is InputAction.ImagePushRowClicked -> viewModelScope.launch { withContext(Dispatchers.Main) { imagePushRowClicked(action.row) } }
        }.exhaustive
    }

    @WorkerThread
    private suspend fun buildPage(articleKey: String) = withContext(Dispatchers.IO) {
        val content = getContentJson(articleKey)
        val model = if (content != null) { ContentTransformer.buildModelFromJson(content) } else null
        if (model != null) {
            withContext(Dispatchers.Main) { replyAction.value = ReplyAction.RenderPage(model) }
            ConfigurationManager.updateConfigurations(listOf(articleKey))
        } else {
            ConfigurationManager.updateConfigurations(listOf(articleKey), configurationAction)
        }
    }

    @MainThread
    private fun imagePushRowClicked(row: ImagePushRow) {
        replyAction.value = ReplyAction.NavigatePage(row.page)
    }

    @MainThread
    private fun showIndex() {
        replyAction.value = ReplyAction.OpenIndexPage("index")
    }

    private fun getContentJson(articleKey: String): JSONObject? {
        val repository by inject(Repository::class.java)
        val content = repository.getPageContents(articleKey)
        content?.let {
            return try {
                JSONObject(it)
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            }
        }
        return null
    }

    fun reply() : LiveData<ReplyAction> = replyAction
    fun configuration() : LiveData<ConfigurationReply> = configurationAction
}

sealed class InputAction {
    data class BuildPage(val articleKey: String) : InputAction()
    data class ImagePushRowClicked(val row: ImagePushRow) : InputAction()

    object     ShowIndex : InputAction()
}

sealed class ReplyAction {
    data class OpenIndexPage(val articleKey: String) : ReplyAction()
    data class NavigatePage(val articleKey: String) : ReplyAction()
    data class RenderPage(val model: List<Model>) : ReplyAction()
}