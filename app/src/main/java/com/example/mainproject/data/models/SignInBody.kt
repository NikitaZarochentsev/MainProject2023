package com.example.mainproject.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInBody(
    var email: String,
    var password: String,
)