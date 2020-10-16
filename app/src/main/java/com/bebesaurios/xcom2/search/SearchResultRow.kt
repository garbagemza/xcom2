package com.bebesaurios.xcom2.search

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.bebesaurios.xcom2.R
import kotlinx.android.synthetic.main.search_result_row.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchResultRow(context: Context) : ConstraintLayout(context) {

    init {
        inflate(getContext(), R.layout.search_result_row, this)
    }

    @TextProp
    fun setText(text: CharSequence) {
        textView.text = text
    }

}

