package com.example.wsrtwo.Api

import retrofit2.Call
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
}