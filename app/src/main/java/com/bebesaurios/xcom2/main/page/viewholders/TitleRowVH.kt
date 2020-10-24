package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.model.TitleRow
import kotlinx.android.synthetic.main.title_row.view.*

class TitleRowVH(val view: View) : PageViewHolder<TitleRow>(view) {

    override fun bind(t: TitleRow) {
        view.titleText.text = t.title
    }

}

