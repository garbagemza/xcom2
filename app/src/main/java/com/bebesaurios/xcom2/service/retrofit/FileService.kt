package com.bebesaurios.xcom2.service.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET


interface FileService {
    @GET("MSI.json")
    fun getMasterSearchIndexMetadata(): Call<ResponseBody>
}