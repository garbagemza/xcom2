package com.bebesaurios.xcom2.search

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bebesaurios.xcom2.R
import kotlinx.android.synthetic.main.search_row.view.*


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchRow (context: Context) : ConstraintLayout(context) {
    init {
        inflate(getContext(), R.layout.search_row, this)
    }

    @TextProp
    fun setInputText(text: CharSequence?) {
        if (setText(editText, text)) {
            // If the text changed then we move the cursor to the end of the new text. This allows us to fill in text programmatically if needed,
            // like a search suggestion, but if the user is typing and the view is rebound we won't lose their cursor position.
            editText.setSelection(editText.length())
        }
    }

    @CallbackProp
    fun onTextChanged(callback: ((String) -> Unit)?) {
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.toString() ?: ""
                callback?.invoke(text)
            }
        })
    }

    private fun setText(editText: ClearableWatcherEditText, text: CharSequence?): Boolean {
        if (!isTextDifferent(text, editText.text)) {
            // Previous text is the same. No op
            return false
        }
        editText.clearTextChangedListeners()
        editText.setText(text)
        return true
    }

    /**
     * @return True if str1 is different from str2.
     *
     *
     * This is adapted from how the Android DataBinding library binds its text views.
     */
    private fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean {
        if (str1 === str2) {
            return false
        }
        if (str1 == null || str2 == null) {
            return true
        }
        val length = str1.length
        if (length != str2.length) {
            return true
        }
        if (str1 is Spanned) {
            return str1 != str2
        }
        for (i in 0 until length) {
            if (str1[i] != str2[i]) {
                return true
            }
        }
        return false
    }
}