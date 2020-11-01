package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.model.ImagePushRow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_push_row.view.*


class ImagePushRowVH(val parent: View) : PageViewHolder<ImagePushRow>(parent) {

    override fun bind(t: ImagePushRow) {
        parent.textView.text = t.text
        parent.setOnClickListener {
            t.clickListener.invoke(t)
        }

        Glide.with(parent).load("https://xcom-2.web.app/images/icon_grenadier.png").into(parent.imageView);

    }
}

