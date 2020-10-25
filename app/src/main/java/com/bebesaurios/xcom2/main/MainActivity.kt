package com.bebesaurios.xcom2.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bebesaurios.xcom2.BaseActivity
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.main.page.InputAction
import com.bebesaurios.xcom2.main.page.PageFragment
import com.bebesaurios.xcom2.main.page.PageViewModel
import com.bebesaurios.xcom2.main.page.ReplyAction
import com.bebesaurios.xcom2.search.SearchActivity
import com.bebesaurios.xcom2.util.addFragment
import com.bebesaurios.xcom2.util.exhaustive
import com.bebesaurios.xcom2.util.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val pageViewModel by viewModel<PageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        subscribe()
        pageViewModel.handle(InputAction.ShowIndex)
    }

    private fun subscribe() {
        pageViewModel.reply().observe(this, { action ->
            action?.let {
                when (it) {
                    is ReplyAction.OpenIndexPage -> loadPage(it.articleKey)
                    is ReplyAction.NavigatePage -> navigatePage(it.articleKey)
                    else -> {}
                }.exhaustive
            }
        })
    }

    private fun navigatePage(articleKey: String) {
        val page = PageFragment.builder()
            .setKey(articleKey)
            .build()
        replaceFragment(R.id.content, page, articleKey)
    }

    private fun loadPage(articleKey: String) {
        val page =
            PageFragment
            .builder()
            .setKey(articleKey)
            .build()
        addFragment(R.id.content, page, articleKey)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_action -> initSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}