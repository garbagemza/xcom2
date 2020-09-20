package com.bebesaurios.xcom2.search

import com.airbnb.epoxy.EpoxyController

class SearchResultsController() : EpoxyController() {
    override fun buildModels() {
//        searchRow {
//            id("searchRow")
//            inputText(inputText)
//            onTextChanged {
//                inputText = it
//                requestDelayedModelBuild(1000)
//            }
//        }

        for (i in 0 until 100) {
            searchResultRow {
                id("view holder $i")
                text("this is a View Holder item")
            }
        }
    }
}
