package com.bebesaurios.xcom2.main.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.main.page.model.ImagePushRow
import com.bebesaurios.xcom2.main.page.model.Model
import com.bebesaurios.xcom2.main.page.model.Rows
import com.bebesaurios.xcom2.main.page.viewholders.*
import com.bebesaurios.xcom2.util.exhaustive

class PageAdapter(private val models: List<Model>, private val handler: (InputAction) -> Unit) : RecyclerView.Adapter<PageViewHolder<Model>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder<Model> {
        val inflater = LayoutInflater.from(parent.context)

        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            Rows.TitleRow.ordinal -> TitleRowVH(inflater.inflate(R.layout.title_row, parent, false))
            Rows.ParagraphRow.ordinal -> ParagraphRowVH(inflater.inflate(R.layout.paragraph_row, parent, false))
            Rows.ImagePushRow.ordinal -> ImagePushRowVH(inflater.inflate(R.layout.image_push_row, parent, false), ::handleInput)
            Rows.ImageRow.ordinal -> ImageRowVH(inflater.inflate(R.layout.image_row, parent, false))
            Rows.TitleBulletPointL1Row.ordinal -> TitleBulletPointL1VH(inflater.inflate(R.layout.normal_bullet_point_l1_row, parent, false))
            Rows.NormalBulletPointL1Row.ordinal -> NormalBulletPointL1VH(inflater.inflate(R.layout.normal_bullet_point_l1_row, parent, false))
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

    private fun handleInput(action: ViewHolderAction) {
        when (action) {
            is ViewHolderAction.ImagePushRowClicked -> handler.invoke(InputAction.ImagePushRowClicked(action.row))
        }.exhaustive
    }
}

sealed class ViewHolderAction {
    data class ImagePushRowClicked(val row: ImagePushRow) : ViewHolderAction()
}