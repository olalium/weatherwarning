package com.projects.lightningwarning.polling
import com.projects.lightningwarning.awssns.AwsSnsService
import com.projects.lightningwarning.lightning.LightningService
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PollingService(
    private val lightningService: LightningService,
    private val awsSnsService: AwsSnsService
) {

    @Scheduled(fixedDelay = 5000) // Every 5 seconds
    fun getNewLightningObservationsWithFixedDelayAndNotifyUser() {
        val newLightningObservations = lightningService.getNewLightningObservations()

        runBlocking {
            awsSnsService.sendLightningObservationsToUserIfWithinMaxDistance(newLightningObservations)
        }
    }
}