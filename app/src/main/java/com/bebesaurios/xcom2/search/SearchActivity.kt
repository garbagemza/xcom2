package com.bebesaurios.xcom2.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.database.Repository
import com.bebesaurios.xcom2.util.exhaustive
import kotlinx.android.synthetic.main.search_activity.*
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s?.toString() ?: ""
                updateModel(searchText)
            }
        })

        editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })

        editText.requestFocus()
    }

    private fun updateModel(searchText: String) {
        val repository : Repository by inject()
        val results = repository.findSearchResults(searchText)
        val adapter = SearchAdapter(results, ::handleInput)
        recyclerView.adapter = adapter
    }

    private fun handleInput(action: InputAction) {
        when (action) {
            is InputAction.ArticleResultClicked -> {
                val data = Intent()
                data.putExtra("article", action.article)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }.exhaustive
    }
}

sealed class InputAction {
    data class ArticleResultClicked(val article: String) : InputAction()
}

// TODO: Move to an extension class
fun Activity.hideKeyboard() {
    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}