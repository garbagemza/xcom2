package com.bebesaurios.xcom2.service.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface FileService {
    @GET("MSI.json")
    fun getMasterSearchIndexMetadata(): Call<ResponseBody>

    @GET("MSI.json?alt=media")
    fun getMasterSearchIndex(): Call<ResponseBody>

    @GET("pages%2F{name}?alt=media")
    fun getPage(@Path("name") filename: String): Call<ResponseBody>
}