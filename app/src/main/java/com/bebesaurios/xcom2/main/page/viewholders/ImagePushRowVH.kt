package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.model.ImagePushRow
import kotlinx.android.synthetic.main.image_push_row.view.*


class ImagePushRowVH(val parent: View) : PageViewHolder<ImagePushRow>(parent) {

    override fun bind(t: ImagePushRow) {
        parent.textView.text = t.text
        parent.setOnClickListener {
            t.clickListener.invoke(t)
        }
    }
}

