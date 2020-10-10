package com.bebesaurios.xcom2.service

import androidx.annotation.WorkerThread
import com.bebesaurios.xcom2.service.retrofit.FileService
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject

object MSIService {

    enum class ServiceCallError(s: String) {
        Unknown("Unknown error.")
    }

    @WorkerThread
    fun getMSIMetadata() : Result<JSONObject, Exception> {
        val service : FileService by inject(FileService::class.java)
        val call = service.getMasterSearchIndexMetadata()
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

    private fun wrapException(error: ServiceCallError) : Exception {
        return Exception(error.toString())
    }

}

sealed class MSIResult {
    data class Success(val json: JSONObject) : MSIResult()
    object Failure : MSIResult()
}

sealed class Result<out T, out E> {
    data class Success<out T, out E>(val value: T) : Result<T, E>()
    data class Failure<out T, out E>(val error: E) : Result<T, E>()
}