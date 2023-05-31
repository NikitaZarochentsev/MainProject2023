package com.example.mainproject.data.models

data class GetProfileResponse(
    val data: Data,
) {
    data class Data(
        val profile: Profile,
    ) {
        data class Profile(
            val name: String,
            val surname: String,
            val occupation: String,
            val avatarId: String,
        ) {
            fun toProfileDomain(): com.example.mainproject.domain.models.Profile {
                return com.example.mainproject.domain.models.Profile(
                    name = name,
                    surname = surname,
                    occupation = occupation,
                    avatarId = avatarId
                )
            }
        }
    }
}