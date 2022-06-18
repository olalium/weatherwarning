package com.projects.lightningwarning.polling
import com.projects.lightningwarning.lightning.LightningService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PollingService(
    private val lightningService: LightningService,
    private val printerService: PrinterService
) {

    @Scheduled(fixedDelay = 1000)
    fun getNewLightningObservationsWithFixedDelayAndPrintResult() {
        val newLightningObservations = lightningService.getNewLightningObservations()
        printerService.printLightningObservations(newLightningObservations)
    }
}