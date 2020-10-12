package com.bebesaurios.xcom2.database

import com.bebesaurios.xcom2.database.entities.ArticleEntity
import com.bebesaurios.xcom2.database.entities.KeywordEntity
import com.bebesaurios.xcom2.database.entities.SearchEntity

class DatabaseFeeder(private val dbModel: DatabaseModel, private val db: AppDatabase) {

    fun start() {

        db.runInTransaction {

            removeAllEntities()

            dbModel.articles.forEach { article ->
                db.articleDao().insert(ArticleEntity(0, article.key, article.contentFile))
            }

            dbModel.keywords.forEach { keyword ->
                db.keywordDao().insert(KeywordEntity(0, keyword.key, keyword.word))
            }

            dbModel.searches.forEach { search ->
                db.searchDao().insert(SearchEntity(0, search.keyword, search.article, search.weight))
            }
        }
        db.close()
    }

    private fun removeAllEntities() {
        db.apply {
            articleDao().deleteAll()
            searchDao().deleteAll()
            keywordDao().deleteAll()
        }
    }
}