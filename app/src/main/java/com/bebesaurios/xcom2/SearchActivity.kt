package com.bebesaurios.xcom2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.bebesaurios.xcom2.search.searchResultRow
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
        val database : Database by inject()
        val keywords = database.keywords.filter { it.word.contains(searchText, ignoreCase = true) }.map { it.key }
        val searches = database.searches.filter { search ->
            keywords.contains(search.keyword)
        }.sortedByDescending { it.weight }

        epoxyRecyclerView.withModels {
            for (search in searches) {
                searchResultRow {
                    id("searchResult ${search.id}")
                    text(search.article)
                }
            }
        }
//        epoxyRecyclerView.withModels {
//
//            for (article in database.articles) {
//                searchResultRow {
//                    id(article.id)
//                    text(article.article)
//                    isStickyHeader(0)
//                }
//            }
//        }

    }


}