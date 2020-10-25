package com.bebesaurios.xcom2.database

import org.json.JSONArray
import org.json.JSONObject

class DatabaseModel(mainJson: JSONObject) {
    val keywords: List<Keyword>
    val searches: List<Search>

    init {
        val keywordsArray = mainJson.getJSONArray("keywords")
        val searchesArray = mainJson.getJSONArray("search")

        keywords = buildKeywords(keywordsArray)
        searches = buildSearches(searchesArray)
    }

    private fun buildKeywords(keywordsArray: JSONArray): List<Keyword> {
        val list = mutableListOf<Keyword>()
        for (i in 0 until keywordsArray.length()) {
            val keywordObject = keywordsArray.getJSONObject(i)
            val key = keywordObject.getString("key")
            val wordsArray = keywordObject.getJSONArray("words")
            for (j in 0 until wordsArray.length()) {
                val word = wordsArray.getString(j)
                val keyword = Keyword(key, word)
                list.add(keyword)
            }
        }
        return list
    }

    private fun buildSearches(searchesArray: JSONArray): List<Search> {
        val list = mutableListOf<Search>()
        for (i in 0 until searchesArray.length()) {
            val searchObject = searchesArray.getJSONObject(i)
            val keyword = searchObject.getString("keyword")
            val article = searchObject.getString("article")
            val title = searchObject.getString("title")
            val weight = searchObject.getInt("weight")
            val search = Search(keyword, article, title, weight)
            list.add(search)
        }
        return list
    }
}

data class Keyword(val key: String, val word: String)
data class Search(val keyword: String, val article: String, val title: String, val weight: Int)