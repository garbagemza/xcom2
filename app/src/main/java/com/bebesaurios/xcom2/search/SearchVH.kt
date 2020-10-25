package com.bebesaurios.xcom2.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bebesaurios.xcom2.database.SearchResult
import kotlinx.android.synthetic.main.search_result_row.view.*

class SearchVH(val parent: View) : RecyclerView.ViewHolder(parent) {

    fun bind(item: SearchResult) {
        parent.textView.text = item.title
    }
}