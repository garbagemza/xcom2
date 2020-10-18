package com.bebesaurios.xcom2.service

import androidx.annotation.WorkerThread
import com.bebesaurios.xcom2.service.retrofit.FileService
import org.json.JSONObject
import org.koin.java.KoinJavaComponent

object PageService : CommonService() {

    @WorkerThread
    fun downloadPage(key: String): Result<JSONObject, Exception> {
        val service: FileService by KoinJavaComponent.inject(FileService::class.java)
        val call = service.getPage(key)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                response.body()?.string()?.let {
                    val json = JSONObject(it)
                    return Result.Success(json)
                }
            }
        } catch (e: Exception) {
            return Result.Failure(e)
        }
        return Result.Failure(wrapException(ServiceCallError.Unknown))
    }

    @WorkerThread
    fun getPageMetadata(key: String): Result<JSONObject, Exception> {
        val service : FileService by KoinJavaComponent.inject(FileService::class.java)
        val call = service.getPageMetadata(key)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                response.body()?.string()?.let { responseString->
                    val json = JSONObject(responseString)
                    return Result.Success(json)
                }
            }
        } catch (e: Exception) {
            return Result.Failure(e)
        }
        return Result.Failure(wrapException(ServiceCallError.Unknown))
    }

}