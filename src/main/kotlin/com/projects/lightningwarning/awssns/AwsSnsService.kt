package com.projects.lightningwarning.awssns

import aws.sdk.kotlin.services.sns.model.PublishRequest
import com.projects.lightningwarning.lightning.location.LocationService
import com.projects.lightningwarning.lightning.ualf.UalfData
import com.projects.lightningwarning.messagecontent.MessageContentService
import com.projects.lightningwarning.user.UserConfig
import org.springframework.stereotype.Component

@Component
class AwsSnsService(
    private val locationService: LocationService,
    private val awsSnsConfig: AwsSnsConfig,
    private val userConfig: UserConfig,
    private val messageContentService: MessageContentService
) {

    suspend fun sendLightningObservationsToUserIfWithinMaxDistance(lightningObservations: List<UalfData>) {
        val lightningObservationsWithinMaxDistance = lightningObservations.filter {
            locationService.locationIsWithinUserMaxDistance(it.longitude, it.latitude)
        }
        if (lightningObservationsWithinMaxDistance.isNotEmpty()) {
            val smsContent = messageContentService.getMessageContentForLightningObservations(lightningObservationsWithinMaxDistance)
            pubTextSMSToUser(smsContent)
        }
    }

    private suspend fun pubTextSMSToUser(messageVal: String?) {
        val request = PublishRequest {
            message = messageVal
            phoneNumber = userConfig.phoneNumber
        }

        awsSnsConfig.amazonSNS().use { snsClient ->
            val result = snsClient.publish(request)
            println("${result.messageId} message sent with following message:\n${messageVal}")
        }
    }
}