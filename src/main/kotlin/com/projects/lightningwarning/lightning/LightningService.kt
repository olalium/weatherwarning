package com.projects.lightningwarning.lightning

import com.projects.lightningwarning.ualf.UalfConverter
import com.projects.lightningwarning.ualf.UalfData
import org.springframework.stereotype.Component
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@Component
class LightningService(
    val restTemplateConfig: RestTemplateConfig,
    val ualfConverter: UalfConverter
) {
    private val registeredLightnings: MutableList<String> = mutableListOf()

    fun getLightningObservations(referenceTime: String = "latest", maxAge: String = "PT30M"): List<UalfData> {
        val stringifiedUalfRows = getLightningDataFromApi(referenceTime, maxAge)
        val lightningObservations = ualfConverter.fromString(stringifiedUalfRows)
        return getNewLightningsObservations(lightningObservations)
    }

    fun distanceBetweenLocations(startLocation: Location, endLocation: Location): Double {
        val theta = startLocation.longitude - endLocation.longitude
        var dist = sin(deg2rad(startLocation.latitude)) * sin(deg2rad(endLocation.latitude)) + cos(deg2rad(startLocation.latitude)) * cos(deg2rad(endLocation.latitude)) * cos(deg2rad(theta))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist *= 1.609344
        return dist
    }

    private fun getLightningDataFromApi (referenceTime: String, maxAge: String): String = restTemplateConfig
        .frostRestTemplate()
        .getForObject(
            "/lightning/v0.ualf?referencetime=${referenceTime}&maxage=${maxAge}",
            String::class.java
        ) ?: ""

    private fun getNewLightningsObservations (lightningObservations: List<UalfData>) = lightningObservations.filter {
        if (registeredLightnings.contains(it.uniqueIdentifier)) {
            false
        } else {
            registeredLightnings.add(it.uniqueIdentifier)
            true
        }
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}