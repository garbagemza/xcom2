package com.bebesaurios.xcom2.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.database.SearchResult

class SearchAdapter(val model: List<SearchResult>, private val callback: (InputAction) -> Unit) : RecyclerView.Adapter<SearchVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val inflater = LayoutInflater.from(parent.context)
        return SearchVH(inflater.inflate(R.layout.search_result_row, parent, false), callback)
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val item = model[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = model.size
}