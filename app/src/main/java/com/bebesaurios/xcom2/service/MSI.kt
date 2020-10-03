package com.bebesaurios.xcom2.service

import androidx.annotation.WorkerThread
import com.bebesaurios.xcom2.service.retrofit.FileService
import com.bebesaurios.xcom2.util.Preferences
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject

@WorkerThread
fun getMSIMetadata() : MSIMetadataResult {
    val preferences: Preferences by inject(Preferences::class.java)
    val service : FileService by inject(FileService::class.java)
    val call = service.getMasterSearchIndexMetadata()
    val response = call.execute()

    if (response.isSuccessful) {
        response.body()?.string()?.let { responseString->
            val json = JSONObject(responseString)
            val newToken = json.getString("updated")
            val msiToken = preferences.getMSIToken()
            if (newToken != msiToken) {
                return MSIMetadataResult.TokenNeedsUpdate(msiToken, newToken)
            }
        }
    }
    return MSIMetadataResult.NoUpdate
}

@WorkerThread
fun downloadMSI() : MSIResult {
    val service : FileService by inject(FileService::class.java)
    val call = service.getMasterSearchIndex()
    val response = call.execute()

    if (response.isSuccessful) {
        response.body()?.string()?.let {
            val json =  JSONObject(it)
            return MSIResult.Success(json)
        }
    }
    return MSIResult.Failure
}

sealed class MSIMetadataResult {
    object NoUpdate : MSIMetadataResult()
    data class TokenNeedsUpdate(val currentToken: String, val newToken: String) : MSIMetadataResult()
}

sealed class MSIResult {
    data class Success(val json: JSONObject) : MSIResult()
    object Failure : MSIResult()
}