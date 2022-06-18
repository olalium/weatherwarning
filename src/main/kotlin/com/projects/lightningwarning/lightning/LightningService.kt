package com.projects.lightningwarning.lightning

import com.projects.lightningwarning.lightning.frostapi.FrostApiService
import com.projects.lightningwarning.ualf.UalfConverter
import com.projects.lightningwarning.ualf.UalfData
import org.springframework.stereotype.Component

@Component
class LightningService(
    val frostApiService: FrostApiService,
    val ualfConverter: UalfConverter
) {
    private val registeredLightnings: MutableList<String> = mutableListOf()

    fun getLightningObservations(referenceTime: String = "latest", maxAge: String = "PT30M"): List<UalfData> {
        val stringifiedUalfRows = frostApiService.getLightningData(referenceTime, maxAge)
        val lightningObservations = ualfConverter.fromString(stringifiedUalfRows)
        return getNewLightningsObservations(lightningObservations)
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