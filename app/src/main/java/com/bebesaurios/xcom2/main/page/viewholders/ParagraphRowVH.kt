package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.model.ParagraphRow
import kotlinx.android.synthetic.main.paragraph_row.view.*

class ParagraphRowVH(val parent: View) : PageViewHolder<ParagraphRow>(parent) {

    override fun bind(t: ParagraphRow) {
        parent.textView.text = t.text
    }
}