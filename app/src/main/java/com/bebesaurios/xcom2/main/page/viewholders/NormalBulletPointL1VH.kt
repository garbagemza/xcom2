package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import com.bebesaurios.xcom2.main.page.model.NormalBulletPointL1Row
import kotlinx.android.synthetic.main.normal_bullet_point_l1_row.view.*

class NormalBulletPointL1VH(private val view: View) : PageViewHolder<NormalBulletPointL1Row>(view) {
    override fun bind(t: NormalBulletPointL1Row) {
        view.textView.text = t.text
    }
}
