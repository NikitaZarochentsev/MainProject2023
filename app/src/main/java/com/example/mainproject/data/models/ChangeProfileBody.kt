package com.example.mainproject.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangeProfileBody(
    val op: String,
    val path: String,
    val value: String,
)