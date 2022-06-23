package com.projects.lightningwarning.awssns

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("aws")
@ConstructorBinding
data class AwsConfig(
    val accessKey: String,
    val secretKey: String,
    val region: String,
)