package com.bebesaurios.xcom2.main.page.views

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bebesaurios.xcom2.R
import kotlinx.android.synthetic.main.paragraph_row.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ParagraphRow(context: Context) : ConstraintLayout(context) {
    init {
        inflate(getContext(), R.layout.paragraph_row, this)
    }

    @TextProp
    fun setText(text: CharSequence) {
        textView.text = text
    }
}