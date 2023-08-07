package com.projects.lightningwarning.lightning

import com.projects.lightningwarning.awssns.AwsSnsService
import com.projects.lightningwarning.lightning.frostapi.FrostApiService
import com.projects.lightningwarning.lightning.location.LocationService
import com.projects.lightningwarning.lightning.ualf.UalfData
import com.projects.lightningwarning.messagecontent.MessageContentService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LightningService(
    private val frostApiService: FrostApiService,
    private val awsSnsService: AwsSnsService,
    private val locationService: LocationService,
    private val messageContentService: MessageContentService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val registeredLightnings: MutableList<String> = mutableListOf()

    suspend fun getFilteredLightningObservationsAndSendToUser(referenceTime: String = "latest", maxAge: String = "PT5M") {
        val lightningObservations = frostApiService.getUalfLightningData(referenceTime, maxAge)
        val filteredObservations = filterLightningObservations(lightningObservations)
        sendLightningObservationsToUser(filteredObservations)
        logger.info("${registeredLightnings.size} lightning observations processed since startup")
    }

    private fun filterLightningObservations (observations: List<UalfData>): List<UalfData> {
        val unregisteredLightningObservations = filterRegisteredObservations(observations)
        return filterObservationsOutsideMaxDistance(unregisteredLightningObservations)
    }

    private fun filterRegisteredObservations (lightningObservations: List<UalfData>) = lightningObservations.filter {
        if (registeredLightnings.contains(it.uniqueIdentifier)) {
            false
        } else {
            registeredLightnings.add(it.uniqueIdentifier)
            true
        }
    }

    private fun filterObservationsOutsideMaxDistance(observations: List<UalfData>) = observations.filter {
        locationService.locationIsWithinUserMaxDistance(it.longitude, it.latitude)
    }

    private suspend fun sendLightningObservationsToUser(observations: List<UalfData>) {
        if (observations.isNotEmpty()) {
            observations.chunked(2).forEach {
                val smsContent = messageContentService.getMessageContentForLightningObservations(it)
                awsSnsService.pubTextSMSToUser(smsContent)
            }
        }
    }
}