package com.example.wsrtwo.Api

import com.example.wsrtwo.Data.Catalog
import com.example.wsrtwo.Data.CatalogItem
import com.example.wsrtwo.Data.News
import com.example.wsrtwo.Data.NewsItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/sendCode")
    fun sendCode (@Header("email") email:String):Call<SendCode>

    @POST("api/signin")
    fun signIn(
        @Header("email")email: String,
        @Header("code") code:String
    ):Call<SignIn>

    @GET("api/catalog")
    suspend fun getCatalog():Response<List<CatalogItem>>

    @GET("api/news")
    suspend fun getNews():Response<List<NewsItem>>
}