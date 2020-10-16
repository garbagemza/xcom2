package com.bebesaurios.xcom2.database

import com.bebesaurios.xcom2.database.entities.*
import org.json.JSONObject


class Repository(private val db: AppDatabase) {

    fun findSearchResults(searchText: String) : List<SearchEntity> {
        val likeArg = "%$searchText%"
        return db.searchDao().find(likeArg)
    }

    fun getMSIToken() : String {
        return db.configurationDao().getConfiguration("master")?.token ?: ""
    }

    fun getPageContents(articleKey: String): String? {
        return db.articleContentDao().get(articleKey)?.content
    }

    fun updateConfiguration(newToken: String, model: DatabaseModel, indexContent: JSONObject) {
        db.runInTransaction {

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

            val index = ArticleContentEntity( "index", indexContent.toString())
            db.articleContentDao().insert(index)

            val configuration = ConfigurationEntity("master", newToken)
            db.configurationDao().insert(configuration)
        }
    }
}