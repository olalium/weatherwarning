package com.projects.lightningwarning.awssns

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import org.springframework.context.annotation.Configuration
import aws.sdk.kotlin.services.sns.SnsClient
import org.springframework.context.annotation.Bean


@Configuration
class AwsSnsConfig(private val awsConfig: AwsConfig) {

    @Bean
    fun amazonSNS(): SnsClient = SnsClient {
            region = awsConfig.region
            credentialsProvider = StaticCredentialsProvider {
                accessKeyId = awsConfig.accessKey
                secretAccessKey = awsConfig.secretKey
            }
        }
}