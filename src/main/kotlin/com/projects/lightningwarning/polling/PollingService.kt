package com.projects.lightningwarning.polling
import com.projects.lightningwarning.lightning.LightningService
import com.projects.lightningwarning.lightning.location.LocationService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Component
class PollingService(
    val lightningService: LightningService,
    val locationService: LocationService
) {

    @Scheduled(fixedDelay = 1000)
    fun getMetData() {
        val latestLightningObservations = lightningService.getLightningObservations()
        latestLightningObservations.forEach {
            val timeOfLightning = ZonedDateTime.of(it.year, it.month, it.day, it.hour, it.minutes, it.seconds, it.nanoseconds, ZoneOffset.UTC)
            println("lightning struck at ${timeOfLightning.toLocalTime()} ${locationService.distanceFromUserInKm(it.longitude, it.latitude)}km away")
            println("it struck ${if(it.cloudIndicator == 0) "air" else "ground"} with a ${it.peakCurrent}kamp current\n")
        }
    }
}