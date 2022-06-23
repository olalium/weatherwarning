package com.projects.lightningwarning.messagecontent

import com.projects.lightningwarning.lightning.location.LocationService
import com.projects.lightningwarning.lightning.ualf.UalfData
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime


@Component
class MessageContentService (
    private val locationService: LocationService
) {

    fun getMessageContentForLightningObservations (lightningObservation: List<UalfData>): String =
        lightningObservation.joinToString("\n") { getMessageContentForLightningObservation(it) }

    private fun getMessageContentForLightningObservation (lightningObservation: UalfData): String {
        return buildString {
            appendLine(getLightningTimeString(lightningObservation))
            appendLine(getLightningDistanceString(lightningObservation))
            appendLine(getLightningPowerString(lightningObservation))
            appendLine(getLightningIndicatorString(lightningObservation))
            appendLine(getGmapsLinkStringToUserLocation(lightningObservation))
        }
    }

    private fun getLightningTimeString (lightningObservation: UalfData): String {
        val timeOfLightningUTC = ZonedDateTime.of(
            lightningObservation.year,
            lightningObservation.month,
            lightningObservation.day,
            lightningObservation.hour,
            lightningObservation.minutes,
            lightningObservation.seconds,
            lightningObservation.nanoseconds,
            ZoneId.of("UTC")
        )
        val timeOfLightningLocalTimeZone = timeOfLightningUTC.withZoneSameInstant(ZoneId.of("Europe/Oslo"))
        return "time: ${timeOfLightningLocalTimeZone.toLocalTime()}"
    }

    private fun getLightningDistanceString (lightningObservation: UalfData): String {
        val distanceFromUser =
            locationService.distanceFromUserInKm(lightningObservation.longitude, lightningObservation.latitude)
        return "distance: ${distanceFromUser}km"
    }

    private fun getLightningIndicatorString (lightningObservation: UalfData): String {
        val cloudIndicatorText = if(lightningObservation.cloudIndicator == 0) "air" else "ground"
        return "type: air to $cloudIndicatorText"
    }

    private fun getLightningPowerString (lightningObservation: UalfData): String =
        "current: ${lightningObservation.peakCurrent}kamp"

    private fun getGmapsLinkStringToUserLocation (lightningObservation: UalfData): String =
        "gmaps: https://www.google.com/maps/search/?api=1&query=${lightningObservation.latitude},${lightningObservation.longitude}"
}