package com.bebesaurios.xcom2.search

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class ClearableWatcherEditText : AppCompatEditText {
    private var mListeners = mutableListOf<TextWatcher>()

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet?, defStyle: Int) : super(ctx, attrs, defStyle)

    override fun addTextChangedListener(watcher: TextWatcher) {
        mListeners.add(watcher)
        super.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher?) {
        val id = mListeners.indexOf(watcher)
        if (id > -1)
            mListeners.removeAt(id)

        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        for (watcher in mListeners) {
            super.removeTextChangedListener(watcher)
        }
        mListeners.clear()
    }
}