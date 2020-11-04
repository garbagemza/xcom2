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
        val rowsArray = content.getJSONArray("rows")
        for (i in 0 until rowsArray.length()) {
            try {
                val rowJson = rowsArray.getJSONObject(i)
                val type = rowJson.getString("type")
                val row = when (type) {
                    "TitleRow" -> buildTitleRow(rowJson)
                    "ParagraphRow" -> buildParagraphRow(rowJson)
                    "ImagePushRow" -> buildImagePushRow(rowJson)
                    "ImageRow" -> buildImageRow(rowJson)
                    "TitleBulletPointL1Row" -> buildTitleBulletPointL1Row(rowJson)
                    "NormalBulletPointL1Row" -> buildNormalBulletPointL1Row(rowJson)
                    else -> throw JSONException("row type not found")
                }.exhaustive
                list.add(row)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
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

    private fun buildTitleBulletPointL1Row(json: JSONObject): Model {
        val subtitle = json.getString("subtitle")
        val text = json.getString("value")
        return TitleBulletPointL1Row(subtitle, text)
    }

    private fun buildNormalBulletPointL1Row(json: JSONObject): Model {
        return NormalBulletPointL1Row(json.getString("value"))
    }

}