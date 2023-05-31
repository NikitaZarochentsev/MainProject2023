package com.example.mainproject.data.models


data class SignInResponse(
    var data: Data,
) {
    data class Data(
        var accessToken: String,
        var profile: Profile,
    )

    data class Profile(
        var name: String,
        var surname: String,
        var occupation: String,
        var avatarId: String,
    )
}