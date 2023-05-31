package com.example.mainproject.domain.models

data class Size(
    val value: String,
    val isAvailable: Boolean,
) {
    constructor(size: Size) : this(size.value, size.isAvailable)
}
