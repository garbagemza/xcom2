package com.bebesaurios.xcom2.database

import org.json.JSONArray
import org.json.JSONObject

class DatabaseModel(mainJson: JSONObject) {
    val keywords: List<Keyword>
    val articles: List<Article>
    val searches: List<Search>

    init {
        val keywordsArray = mainJson.getJSONArray("keywords")
        val articlesArray = mainJson.getJSONArray("articles")
        val searchesArray = mainJson.getJSONArray("search")

        keywords = buildKeywords(keywordsArray)
        articles = buildArticles(articlesArray)
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

    private fun buildArticles(articlesArray: JSONArray): List<Article> {
        val list = mutableListOf<Article>()
        for (i in 0 until articlesArray.length()) {
            val articleObject = articlesArray.getJSONObject(i)
            val key = articleObject.getString("key")
            val articleString = articleObject.getString("content")
            val article = Article(key, articleString)
            list.add(article)
        }
        return list
    }

    private fun buildSearches(searchesArray: JSONArray): List<Search> {
        val list = mutableListOf<Search>()
        for (i in 0 until searchesArray.length()) {
            val searchObject = searchesArray.getJSONObject(i)
            val keyword = searchObject.getString("keyword")
            val article = searchObject.getString("article")
            val weight = searchObject.getInt("weight")
            val search = Search(keyword, article, weight)
            list.add(search)
        }
        return list
    }
}

data class Keyword(val key: String, val word: String)
data class Article(val key: String, val contentFile: String)
data class Search(val keyword: String, val article: String, val weight: Int)