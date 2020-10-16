package com.bebesaurios.xcom2.main.page

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bebesaurios.xcom2.util.SingleLiveEvent
import com.bebesaurios.xcom2.util.exhaustive

class PageViewModel : ViewModel() {
    private val replyAction = SingleLiveEvent<ReplyAction>()

    @MainThread
    fun handle(action: InputAction) {
        when (action) {
            InputAction.ShowIndex -> showIndex()
        }.exhaustive
    }

    @MainThread
    private fun showIndex() {
        replyAction.value = ReplyAction.LoadPage("index")
//        thread {
//            val repository by inject(Repository::class.java)
//            val content = repository.getPageContents("index")
//            content?.let {
//                try {
//                    val json = JSONObject(it)
//
//                } catch (e: JSONException) {
//
//                }
//
//            }
//            Log.i("pageViewModel", content ?: "")
//        }
    }

    fun reply() : LiveData<ReplyAction> = replyAction
}

sealed class InputAction {
    object ShowIndex : InputAction()
}

sealed class ReplyAction {
    data class LoadPage(val articleKey: String) : ReplyAction()
}