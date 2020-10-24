package com.bebesaurios.xcom2.main.page.views

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bebesaurios.xcom2.R
import kotlinx.android.synthetic.main.image_push_row.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ImagePushRow(context: Context) : ConstraintLayout(context) {

    init {
        inflate(getContext(), R.layout.image_push_row, this)
    }

    @TextProp
    fun setText(text: CharSequence) {
        textView.text = text
    }

}