package com.projects.lightningwarning.ualf

import org.springframework.stereotype.Component

@Component
class UalfConverter {

    fun fromString(ualfString: String): List<UalfData> =
        ualfString
            .split("\n")
            .dropLast(1)
            .map { lightningDataRow ->
                val lightningDataColumns = lightningDataRow.split(" ")
                UalfData(
                    version = Integer.parseInt(lightningDataColumns[0]),
                    year = Integer.parseInt(lightningDataColumns[1]),
                    month = Integer.parseInt(lightningDataColumns[2]),
                    day = Integer.parseInt(lightningDataColumns[3]),
                    hour = Integer.parseInt(lightningDataColumns[4]),
                    minutes = Integer.parseInt(lightningDataColumns[5]),
                    seconds = Integer.parseInt(lightningDataColumns[6]),
                    nanoseconds = Integer.parseInt(lightningDataColumns[7]),
                    latitude = lightningDataColumns[8].toDouble(),
                    longitude = lightningDataColumns[9].toDouble(),
                    peakCurrent = Integer.parseInt(lightningDataColumns[10]),
                    multiplicity = Integer.parseInt(lightningDataColumns[11]),
                    numberOfSensors = Integer.parseInt(lightningDataColumns[12]),
                    degreesOfFreedom = Integer.parseInt(lightningDataColumns[13]),
                    ellipseAngle = lightningDataColumns[14].toDouble(),
                    semiMajorAxis = lightningDataColumns[15].toDouble(),
                    semiMinorAxis = lightningDataColumns[16].toDouble(),
                    chiSquareValue = lightningDataColumns[17].toDouble(),
                    riseTime = lightningDataColumns[18].toDouble(),
                    peakToZeroTime = lightningDataColumns[19].toDouble(),
                    maxRateOfRise = lightningDataColumns[20].toDouble(),
                    cloudIndicator = Integer.parseInt(lightningDataColumns[21]),
                    angleIndicator = Integer.parseInt(lightningDataColumns[22]),
                    signalIndicator = Integer.parseInt(lightningDataColumns[23]),
                    timingIndicator = Integer.parseInt(lightningDataColumns[24]),
                    uniqueIdentifier =
                        lightningDataColumns[1] +
                        lightningDataColumns[2] +
                        lightningDataColumns[3] +
                        lightningDataColumns[4] +
                        lightningDataColumns[5] +
                        lightningDataColumns[6] +
                        lightningDataColumns[7]
                )
            }.reversed()
}