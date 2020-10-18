package com.bebesaurios.xcom2.database

import com.bebesaurios.xcom2.database.entities.*
import org.json.JSONObject


class Repository(private val db: AppDatabase) {

    fun findSearchResults(searchText: String) : List<SearchEntity> {
        val likeArg = "%$searchText%"
        return db.searchDao().find(likeArg)
    }

    fun getConfiguration(key: String): ConfigurationEntity? {
        return db.configurationDao().getConfiguration(key)
    }

    fun getPageContents(articleKey: String): String? {
        return db.articleContentDao().get(articleKey)?.content
    }

    fun saveConfiguration(key: String, json: JSONObject, newToken: String) {
        when (key) {
            "master" -> saveMasterConfiguration(key, json, newToken)
                else -> savePageConfiguration(key, json, newToken)
        }
    }

    private fun saveMasterConfiguration(key: String, json: JSONObject, newToken: String) {
        db.runInTransaction {
            val model = DatabaseModel(json)

            db.apply {
                articleDao().deleteAll()
                searchDao().deleteAll()
                keywordDao().deleteAll()
            }

            model.articles.forEach { article ->
                db.articleDao().insert(ArticleEntity(0, article.key, article.contentFile))
            }

            model.keywords.forEach { keyword ->
                db.keywordDao().insert(KeywordEntity(0, keyword.key, keyword.word))
            }

            model.searches.forEach { search ->
                db.searchDao().insert(SearchEntity(0, search.keyword, search.article, search.weight))
            }

            val configuration = ConfigurationEntity(key, newToken)
            db.configurationDao().insert(configuration)
        }
    }

    private fun savePageConfiguration(key: String, json: JSONObject, newToken: String) {
        db.runInTransaction {
            val pageContent = ArticleContentEntity( key, json.toString())
            db.articleContentDao().insert(pageContent)

            val configuration = ConfigurationEntity(key, newToken)
            db.configurationDao().insert(configuration)
        }
    }

    private fun getUpdatedToken(metadata: JSONObject): String {
        return metadata.getString("updated")
    }
}