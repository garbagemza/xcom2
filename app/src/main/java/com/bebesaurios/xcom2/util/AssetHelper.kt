package com.bebesaurios.xcom2.util

import android.content.res.AssetManager
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException

class AssetHelper(private val assetManager: AssetManager) {

    fun openResourceAsString(fileName: String): String {
        val buffer = ByteArrayOutputStream()
        try {
            val `is` = assetManager.open(fileName)
            var nRead: Int
            val data = ByteArray(10240)
            while (`is`.read(data, 0, data.size).also { nRead = it } != -1) {
                buffer.write(data, 0, nRead)
                buffer.flush()
            }
        } catch (e1: IOException) {
            Log.e("AssetHelper","Unable to openResourceAsString: IOException")
        }
        return buffer.toString()
    }

}