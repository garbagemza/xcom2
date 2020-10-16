package com.bebesaurios.xcom2.main.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bebesaurios.xcom2.R

class PageFragment : Fragment() {

    companion object {
        fun builder() = PageFragmentBuilder()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_activity, container, false)
    }
}