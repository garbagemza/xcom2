package com.bebesaurios.xcom2.main.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.main.page.model.LoadingStatus
import com.bebesaurios.xcom2.main.page.model.LoadingStatusRows
import com.bebesaurios.xcom2.main.page.viewholders.ErrorVH
import com.bebesaurios.xcom2.main.page.viewholders.LoadingVH
import com.bebesaurios.xcom2.main.page.viewholders.PageViewHolder

class LoadingStatusAdapter(val model: List<LoadingStatus>) : RecyclerView.Adapter<PageViewHolder<LoadingStatus>>() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder<LoadingStatus> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LoadingStatusRows.LoadingRow.ordinal -> LoadingVH(inflater.inflate(R.layout.loading_row, parent, false))
            LoadingStatusRows.ErrorRow.ordinal -> ErrorVH(inflater.inflate(R.layout.error_row, parent, false))
            else -> throw RuntimeException("view type not found")
        } as PageViewHolder<LoadingStatus>
    }

    override fun getItemViewType(position: Int): Int {
        val item = model[position]
        return item.type
    }

    override fun onBindViewHolder(holder: PageViewHolder<LoadingStatus>, position: Int) {
        val item = model[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = model.size

}
