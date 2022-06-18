package com.projects.lightningwarning.lightning.ualf

data class UalfData(
    val version: Int,
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minutes: Int,
    val seconds: Int,
    val nanoseconds: Int,
    val latitude: Double,
    val longitude: Double,
    val peakCurrent: Int, // estimated peak current in kilo amps
    val multiplicity: Int,
    val numberOfSensors: Int,
    val degreesOfFreedom: Int,
    val ellipseAngle: Double,
    val semiMajorAxis: Double,
    val semiMinorAxis: Double,
    val chiSquareValue: Double,
    val riseTime: Double,
    val peakToZeroTime: Double,
    val maxRateOfRise: Double,
    val cloudIndicator: Int,  // 1 / 0, 1 = cloud->cloud, 0 = cloud->ground
    val angleIndicator: Int,
    val signalIndicator: Int,
    val timingIndicator: Int,
    val uniqueIdentifier: String
)
