package com.projects.lightningwarning.lightning.location

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@Component
class LocationService(private val locationConfig: LocationConfig) {

    fun distanceFromUserInKm(longitude: Double, latitude: Double, precision: Int = 1): BigDecimal {
        val theta = locationConfig.longitude - longitude
        var dist = sin(deg2rad(locationConfig.latitude)) * sin(deg2rad(latitude)) +
                cos(deg2rad(locationConfig.latitude)) * cos(deg2rad(latitude)) * cos(deg2rad(theta))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist *= 1.609344
        return BigDecimal(dist).setScale(precision, RoundingMode.HALF_UP)
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}