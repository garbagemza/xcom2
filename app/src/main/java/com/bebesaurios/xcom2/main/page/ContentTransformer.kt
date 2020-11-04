package com.bebesaurios.xcom2.main.page

import androidx.annotation.WorkerThread
import com.bebesaurios.xcom2.main.page.model.*
import com.bebesaurios.xcom2.util.exhaustive
import org.json.JSONException
import org.json.JSONObject

// collaborator of PageViewModel, transforms page content json data into Model items
object ContentTransformer {

    @WorkerThread
    fun buildModelFromJson(content: JSONObject): List<Model> {
        val list = mutableListOf<Model>()
        try {
            val rowsArray = content.getJSONArray("rows")
            for (i in 0 until rowsArray.length()) {
                val rowJson = rowsArray.getJSONObject(i)
                val type = rowJson.getString("type")
                val row = when (type) {
                    "TitleRow" -> buildTitleRow(rowJson)
                    "ParagraphRow" -> buildParagraphRow(rowJson)
                    "ImagePushRow" -> buildImagePushRow(rowJson)
                    "ImageRow" -> buildImageRow(rowJson)
                    else -> throw JSONException("row type not found")
                }.exhaustive
                list.add(row)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    private fun buildImageRow(json: JSONObject): Model {
        val imageUrl = json.getString("image")
        return ImageRow(imageUrl)
    }

    private fun buildImagePushRow(json: JSONObject): Model {
        val text = json.getString("text")
        val imageUrl = json.getString("image")
        val page = json.getString("page")
        return ImagePushRow(text, imageUrl, page)
    }

    private fun buildTitleRow(json: JSONObject): Model {
        val value = json.getString("value")
        return TitleRow(value)
    }

    private fun buildParagraphRow(json: JSONObject): Model {
        return ParagraphRow(json.getString("value"))
    }

}