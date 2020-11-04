package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.ViewHolderAction
import com.bebesaurios.xcom2.main.page.model.ImagePushRow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_push_row.view.*


class ImagePushRowVH(val parent: View, private val handler: (ViewHolderAction) -> Unit) : PageViewHolder<ImagePushRow>(parent) {

    override fun bind(t: ImagePushRow) {
        parent.textView.text = t.text
        parent.setOnClickListener {
            handler.invoke(ViewHolderAction.ImagePushRowClicked(t))
        }

        Glide.with(parent).load(t.imageUrl).into(parent.imageView);

    }
}

