package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.model.ImageRow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_row.view.*

class ImageRowVH(private val parent: View) : PageViewHolder<ImageRow>(parent) {
    override fun bind(t: ImageRow) {
        Glide.with(parent)
            .load(t.imageUrl)
            .centerCrop()
            .into(parent.imageView)
    }
}