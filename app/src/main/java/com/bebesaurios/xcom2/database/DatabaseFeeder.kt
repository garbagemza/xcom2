package com.bebesaurios.xcom2.database

import com.bebesaurios.xcom2.database.entities.ArticleEntity
import com.bebesaurios.xcom2.database.entities.ArticleTranslationEntity
import com.bebesaurios.xcom2.database.entities.KeywordEntity
import com.bebesaurios.xcom2.database.entities.SearchEntity

class DatabaseFeeder(private val dbModel: DatabaseModel, private val db: AppDatabase) {

    fun start() {

        db.runInTransaction {
            dbModel.articles.forEach { article ->
                db.articleDao().insert(ArticleEntity(0, article.key, article.article))
            }

            dbModel.keywords.forEach { keyword ->
                db.keywordDao().insert(KeywordEntity(0, keyword.key, keyword.word))
            }

            dbModel.searches.forEach { search ->
                db.searchDao().insert(SearchEntity(0, search.keyword, search.article, search.weight))
            }

            dbModel.translations.forEach {
                db.articleTranslationDao().insert(ArticleTranslationEntity(id = 0, key = it.key, locale = it.locale, translation = it.translation))
            }
        }
        db.close()
    }
}