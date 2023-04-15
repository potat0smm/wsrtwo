package com.example.wsrtwo.Api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class SendCode(
    @SerializedName("message")
    val message: String
):Parcelable

@Parcelize
data class SignIn(
    @SerializedName("token")
    val token:String
):Parcelable
