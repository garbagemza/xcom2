package com.bebesaurios.xcom2.main.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.main.page.model.Model
import com.bebesaurios.xcom2.main.page.model.Rows
import com.bebesaurios.xcom2.main.page.viewholders.ImagePushRowVH
import com.bebesaurios.xcom2.main.page.viewholders.PageViewHolder
import com.bebesaurios.xcom2.main.page.viewholders.ParagraphRowVH
import com.bebesaurios.xcom2.main.page.viewholders.TitleRowVH

class PageAdapter(private val models: List<Model>) : RecyclerView.Adapter<PageViewHolder<Model>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder<Model> {
        val inflater = LayoutInflater.from(parent.context)

        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            Rows.TitleRow.ordinal -> TitleRowVH(inflater.inflate(R.layout.title_row, parent, false))
            Rows.ParagraphRow.ordinal -> ParagraphRowVH(inflater.inflate(R.layout.paragraph_row, parent, false))
            Rows.ImagePushRow.ordinal -> ImagePushRowVH(inflater.inflate(R.layout.image_push_row, parent, false))
            else -> throw RuntimeException("Invalid view type")
        } as PageViewHolder<Model>
    }

    override fun getItemViewType(position: Int): Int {
        val model = models[position]
        return model.type.ordinal
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: PageViewHolder<Model>, position: Int) {
        val model = models[position]
        holder.bind(model)
    }

}