package com.bebesaurios.xcom2.main.page.views

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bebesaurios.xcom2.R
import kotlinx.android.synthetic.main.image_push_row.view.*


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ImagePushRow(context: Context) : ConstraintLayout(context) {

    @ModelProp
    lateinit var pushProp: PushProp

    init {
        inflate(getContext(), R.layout.image_push_row, this)
    }

    @TextProp
    fun setText(text: CharSequence) {
        textView.text = text
    }

    @TextProp
    fun setImage(image: CharSequence) {
        //todo set image with glide
    }

    @CallbackProp
    fun clickListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }
}

data class PushProp(val page: String)