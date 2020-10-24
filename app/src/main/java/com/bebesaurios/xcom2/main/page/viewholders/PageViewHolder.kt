package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class PageViewHolder<in T>(parent: View) : RecyclerView.ViewHolder(parent) {
    abstract fun bind(t: T)
}