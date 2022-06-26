package com.projects.lightningwarning.awssns

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import com.projects.lightningwarning.lightning.location.LocationService
import com.projects.lightningwarning.lightning.ualf.UalfData
import com.projects.lightningwarning.messagecontent.MessageContentService
import com.projects.lightningwarning.user.UserConfig
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AwsSnsService(
    private val awsConfig: AwsConfig,
    private val userConfig: UserConfig,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    suspend fun pubTextSMSToUser(messageVal: String?) {
        val request = PublishRequest {
            message = messageVal
            phoneNumber = userConfig.phoneNumber
        }

        getAmazonSNS().use { snsClient ->
            val result = snsClient.publish(request)
            logger.info("${result.messageId} message sent with following message:\n${messageVal}")
        }
    }

    private fun getAmazonSNS(): SnsClient = SnsClient {
        region = awsConfig.region
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = awsConfig.accessKey
            secretAccessKey = awsConfig.secretKey
        }
    }
}