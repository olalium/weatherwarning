package com.projects.lightningwarning.lightning.frostapi

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("met.client")
@ConstructorBinding
data class FrostApiConfig (
    val id: String,
    val secret: String
)