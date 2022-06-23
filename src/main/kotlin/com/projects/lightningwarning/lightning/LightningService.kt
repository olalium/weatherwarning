package com.projects.lightningwarning.lightning

import aws.smithy.kotlin.runtime.util.length
import com.projects.lightningwarning.lightning.frostapi.FrostApiService
import com.projects.lightningwarning.lightning.ualf.UalfConverter
import com.projects.lightningwarning.lightning.ualf.UalfData
import org.springframework.stereotype.Component

@Component
class LightningService(
    private val frostApiService: FrostApiService,
    private val ualfConverter: UalfConverter
) {
    private val registeredLightnings: MutableList<String> = mutableListOf()

    fun getNewLightningObservations(referenceTime: String = "latest", maxAge: String = "PT30M"): List<UalfData> {
        val stringifiedUalfRows = frostApiService.getLightningData(referenceTime, maxAge)
        val lightningObservations = ualfConverter.fromString(stringifiedUalfRows)
        val newLightningObservations = getNewLightningsObservations(lightningObservations)
        if (newLightningObservations.length > 0)
            println("${newLightningObservations.length} lightning observations found")
        return newLightningObservations
    }

    private fun getNewLightningsObservations (lightningObservations: List<UalfData>) = lightningObservations.filter {
        if (registeredLightnings.contains(it.uniqueIdentifier)) {
            false
        } else {
            registeredLightnings.add(it.uniqueIdentifier)
            true
        }
    }

}