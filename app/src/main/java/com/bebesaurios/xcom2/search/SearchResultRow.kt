package com.bebesaurios.xcom2.search

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.bebesaurios.xcom2.R
import kotlinx.android.synthetic.main.search_result_row.view.*

class SearchResultRow(context: Context) : ConstraintLayout(context) {

    init {
        inflate(getContext(), R.layout.search_result_row, this)
    }

    fun setText(text: CharSequence) {
        textView.text = text
    }

}