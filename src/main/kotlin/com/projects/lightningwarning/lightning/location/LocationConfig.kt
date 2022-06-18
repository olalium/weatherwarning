package com.projects.lightningwarning.lightning.location

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("user.location")
@ConstructorBinding
data class LocationConfig (
    val latitude: Double,
    val longitude: Double
)