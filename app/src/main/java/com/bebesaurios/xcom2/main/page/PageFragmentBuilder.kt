package com.bebesaurios.xcom2.main.page

import android.os.Bundle

class PageFragmentBuilder {
    private lateinit var key : String
    fun setKey(key: String) : PageFragmentBuilder {
        this.key = key
        return this
    }

    fun build() : PageFragment {
        val fragment = PageFragment()
        val bundle = Bundle()
        bundle.putString("articleKey", this.key)
        fragment.arguments = bundle
        return fragment
    }

}