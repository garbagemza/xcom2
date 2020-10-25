package com.bebesaurios.xcom2.main.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.replaceFragment
import kotlinx.android.synthetic.main.page_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PageFragment : Fragment() {

    private val pageViewModel by sharedViewModel(PageViewModel::class)

    companion object {
        fun builder() = PageFragmentBuilder()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        val arguments = wrap(arguments)
        pageViewModel.handle(InputAction.BuildPage(arguments.articleKey))
    }

    private fun subscribe() {
        pageViewModel.reply().observe(viewLifecycleOwner, {
            it?.let {
                when(it) {
                    is ReplyAction.OpenIndexPage -> {}
                    is ReplyAction.NavigatePage -> {}
                    is ReplyAction.RenderPage -> {
                        val adapter = PageAdapter(it.model)
                        recyclerView.adapter = adapter
                    }
                }.exhaustive
            }
        })
    }

    private fun wrap(args: Bundle?) : Args {
        return Args(articleKey = args?.getString("articleKey", null) ?: "index")
    }
}

data class Args(val articleKey: String)