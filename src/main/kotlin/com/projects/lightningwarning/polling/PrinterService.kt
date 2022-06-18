package com.projects.lightningwarning.polling

import com.projects.lightningwarning.lightning.location.LocationService
import com.projects.lightningwarning.lightning.ualf.UalfData
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Component
class PrinterService(
    private val locationService: LocationService
) {

    fun printLightningObservations (lightningObservations: List<UalfData>) =
        lightningObservations.forEach {
            val timeOfLightning = ZonedDateTime.of(it.year, it.month, it.day, it.hour, it.minutes, it.seconds, it.nanoseconds, ZoneOffset.UTC)
            println("lightning struck at ${timeOfLightning.toLocalTime()} ${locationService.distanceFromUserInKm(it.longitude, it.latitude)}km away")
            println("it struck ${if(it.cloudIndicator == 0) "air" else "ground"} with a ${it.peakCurrent}kamp current\n")
        }
}