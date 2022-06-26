package com.projects.lightningwarning.polling
import com.projects.lightningwarning.lightning.LightningService
import com.projects.lightningwarning.lightning.location.LocationService
import com.projects.lightningwarning.polling.lightningqueue.LightningQueue
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PollingService(
        private val lightningQueue: LightningQueue,
        private val locationService: LocationService,
        private val lightningService: LightningService,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(fixedDelay = 100)
    fun getItemsFromQueueAndTriggerLightningServiceIfWithinUserMaxDistance() {
        var shouldGetNewLightningObservations = false

        if (lightningQueue.notEmpty())
            logger.info("pulled ${lightningQueue.getSize()} items from lightning queue")

        while (lightningQueue.notEmpty()) {
            val item = lightningQueue.getAndRemoveItemFromQueue()
            if (locationService.locationIsWithinUserMaxDistance(item.Point[0], item.Point[1])) {
                shouldGetNewLightningObservations = true
            }
        }

        if (shouldGetNewLightningObservations) {
            logger.info("lightning observed within user max distance")
            runBlocking {
                lightningService.getFilteredLightningObservationsAndSendToUser()
            }
        }
    }
}