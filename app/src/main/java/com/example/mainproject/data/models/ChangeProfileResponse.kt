package com.example.mainproject.data.models

data class ChangeProfileResponse(
    val data: Profile,
) {
    data class Profile(
        val name: String,
        val surname: String,
        val occupation: String,
        val avatarId: String,
    )
}