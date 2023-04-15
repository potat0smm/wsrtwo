package com.example.wsrtwo

import androidx.lifecycle.ViewModel


class SignViewModel: ViewModel() {
    var token:String? = null

    fun saveToken(token: String) {
        this.token = token
    }

}