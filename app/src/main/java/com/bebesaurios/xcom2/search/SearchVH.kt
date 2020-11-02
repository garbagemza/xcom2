package com.bebesaurios.xcom2.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bebesaurios.xcom2.database.SearchResult
import kotlinx.android.synthetic.main.search_result_row.view.*

class SearchVH(private val parent: View, private val callback: (InputAction) -> Unit) : RecyclerView.ViewHolder(parent) {

    fun bind(item: SearchResult) {
        parent.textView.text = item.title
        parent.setOnClickListener {
            callback.invoke(InputAction.ArticleResultClicked(item.articleKey))
        }
    }
}