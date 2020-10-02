package com.bebesaurios.xcom2.service

import android.util.Log
import androidx.annotation.WorkerThread
import com.bebesaurios.xcom2.service.retrofit.FileService
import com.bebesaurios.xcom2.util.Preferences
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject

@WorkerThread
fun getMSIMetadata() : MSIMetadataResult {
    val preferences: Preferences by inject(Preferences::class.java)
    if (preferences.getMSIToken().isEmpty())
        return MSIMetadataResult.TokenDoesNotExist

    val service : FileService by inject(FileService::class.java)
    val call = service.getMasterSearchIndexMetadata()
    val response = call.execute()

    if (response.isSuccessful) {
        response.body()?.string()?.let { responseString->
            val json = JSONObject(responseString)
            val newToken = json.getString("updated")
            val msiToken = preferences.getMSIToken()
            if (newToken != msiToken) {
                return MSIMetadataResult.TokenNeedsUpdate(newToken)
            }
        }
    }
    return MSIMetadataResult.NoUpdate
}

sealed class MSIMetadataResult {
    object NoUpdate : MSIMetadataResult()
    object TokenDoesNotExist : MSIMetadataResult()
    data class TokenNeedsUpdate(val newToken: String) : MSIMetadataResult()
}