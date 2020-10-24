package com.bebesaurios.xcom2.main.page

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.epoxy.EpoxyModel
import com.bebesaurios.xcom2.database.Repository
import com.bebesaurios.xcom2.main.page.views.ImagePushRowModel_
import com.bebesaurios.xcom2.main.page.views.ParagraphRowModel_
import com.bebesaurios.xcom2.main.page.views.PushProp
import com.bebesaurios.xcom2.main.page.views.TitleRowModel_
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.postMainThread
import org.json.JSONException
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject
import kotlin.concurrent.thread

class PageViewModel : ViewModel() {
    private val replyAction = MutableLiveData<ReplyAction>()

    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.ShowIndex -> showIndex()
            is InputAction.BuildPage -> buildPage(action.articleKey)
        }.exhaustive
    }

    private fun buildPage(articleKey: String) {
        thread {
            val content = getContentJson(articleKey)
            val model = if (content != null) {
                buildModelFromJson(content)
            } else null

            model?.let {
                postMainThread {
                    replyAction.value = ReplyAction.RenderPage(model)
                }
            }
        }
    }

    @WorkerThread
    private fun buildModelFromJson(content: JSONObject): List<EpoxyModel<*>> {
        val list = mutableListOf<EpoxyModel<*>>()
        try {
            val rowsArray = content.getJSONArray("rows")
            for (i in 0 until rowsArray.length()) {
                val rowJson = rowsArray.getJSONObject(i)
                val type = rowJson.getString("type")
                val row = when (type) {
                    "TitleRow" -> buildTitleRow(rowJson)
                    "ParagraphRow" -> buildParagraphRow(rowJson)
                    "ImagePushRow" -> buildImagePushRow(rowJson)
                    else -> throw JSONException("row type not found")
                }.exhaustive
                list.add(row)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    private fun buildImagePushRow(json: JSONObject): EpoxyModel<out Any> {
        val id = json.getString("id")
        val text = json.getString("text")
        val image = json.getString("image")
        val page = json.getString("page")
        return ImagePushRowModel_()
            .id(id)
            .text(text)
            .pushProp(PushProp(page))
            .image(image)
            .clickListener { model, _, _, _ ->
                val pushProp = model.pushProp()
                replyAction.value = ReplyAction.NavigatePage(pushProp.page)
            }
    }

    private fun buildTitleRow(json: JSONObject): EpoxyModel<*> {
        val id = json.getString("id")
        val value = json.getString("value")

        return TitleRowModel_()
            .id(id)
            .text(value)
    }

    private fun buildParagraphRow(json: JSONObject): EpoxyModel<*> {
        return ParagraphRowModel_()
            .id(json.getString("id"))
            .text(json.getString("value"))

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
}

sealed class InputAction {
    data class BuildPage(val articleKey: String) : InputAction()
    object     ShowIndex : InputAction()
}

sealed class ReplyAction {
    data class OpenIndexPage(val articleKey: String) : ReplyAction()
    data class NavigatePage(val articleKey: String) : ReplyAction()
    data class RenderPage(val model: List<EpoxyModel<*>>) : ReplyAction()
}