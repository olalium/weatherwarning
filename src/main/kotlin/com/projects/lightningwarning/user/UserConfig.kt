package com.projects.lightningwarning.user

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("user")
@ConstructorBinding
data class UserConfig (
    val latitude: Double,
    val longitude: Double,
    val maxDistance: Double,
    val phoneNumber: String
)
