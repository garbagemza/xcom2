package com.bebesaurios.xcom2.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.database.Repository
import kotlinx.android.synthetic.main.search_activity.*
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s?.toString() ?: ""
                updateModel(searchText)
            }
        })
    }

    private fun updateModel(searchText: String) {
        val repository : Repository by inject()
        val results = repository.findSearchResults(searchText)
        epoxyRecyclerView.withModels {
            for (result in results) {
                searchResultRow {
                    id("searchResult ${result.id}")
                    text(result.article)
                }
            }
        }

    }


}