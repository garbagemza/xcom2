package com.bebesaurios.xcom2.main.page.viewholders

import android.view.View
import androidx.core.text.HtmlCompat
import com.bebesaurios.xcom2.main.page.model.TitleBulletPointL1Row
import kotlinx.android.synthetic.main.normal_bullet_point_l1_row.view.*

class TitleBulletPointL1VH(private val view: View) : PageViewHolder<TitleBulletPointL1Row>(view) {
    override fun bind(t: TitleBulletPointL1Row) {
        view.textView.text = HtmlCompat.fromHtml("<b>${t.subtitleText}:</b> ${t.text}", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
