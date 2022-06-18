package com.projects.lightningwarning.polling

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("met.client")
@ConstructorBinding
data class PollingServiceConfig (
    val id: String,
    val secret: String
)