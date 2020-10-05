package com.bebesaurios.xcom2.database

import org.json.JSONArray
import org.json.JSONObject

class DatabaseModel(mainJson: JSONObject) {
    val keywords: List<Keyword>
    val articles: List<Article>
    val translations: List<ArticleTranslation>
    val searches: List<Search>

    init {
        val keywordsArray = mainJson.getJSONArray("keywords")
        val articlesArray = mainJson.getJSONArray("articles")
        val translationsArray = mainJson.getJSONArray("articleTranslations")
        val searchesArray = mainJson.getJSONArray("search")

        keywords = buildKeywords(keywordsArray)
        articles = buildArticles(articlesArray)
        translations = buildArticleTranslations(translationsArray)
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
            val articleString = articleObject.getString("article")
            val article = Article(key, articleString)
            list.add(article)
        }
        return list
    }

    private fun buildArticleTranslations(translationsArray: JSONArray): List<ArticleTranslation> {
        val list = mutableListOf<ArticleTranslation>()
        for (i in 0 until translationsArray.length()) {
            val translationObject = translationsArray.getJSONObject(i)
            val key = translationObject.getString("key")
            val locale = translationObject.getString("locale")
            val translation = translationObject.getString("translation")
            val articleTranslation = ArticleTranslation(key, locale, translation)
            list.add(articleTranslation)
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
data class Article(val key: String, val article: String)
data class ArticleTranslation(val key: String, val locale: String, val translation: String)
data class Search(val keyword: String, val article: String, val weight: Int)