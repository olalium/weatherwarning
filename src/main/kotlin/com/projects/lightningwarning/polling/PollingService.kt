package com.projects.lightningwarning.polling
import com.projects.lightningwarning.lightning.LightningService
import com.projects.lightningwarning.lightning.Location
import com.projects.lightningwarning.lightning.LocationConfig
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.math.roundToInt

@Component
class PollingService(
    val lightningService: LightningService,
    val locationConfig: LocationConfig
) {

    @Scheduled(fixedDelay = 1000)
    fun getMetData() {
        val last30MinOfLightningData = lightningService.getLightningObservations(maxAge = "PT60S")
        last30MinOfLightningData.forEach {
            val distanceBetweenUserAndLightning = lightningService.distanceBetweenLocations(Location(locationConfig.latitude, locationConfig.latitude), Location(it.latitude, it.longitude)).roundToInt()
            println("lightning struck at ${it.hour}:${it.minutes} ${distanceBetweenUserAndLightning}km away")
            println("it struck ${if(it.cloudIndicator == 0) "air to air" else "air to ground"} with a ${it.peakCurrent}kamp current\n")
        }
    }
}