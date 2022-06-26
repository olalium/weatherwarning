package com.projects.lightningwarning.lightning.frostapi

import com.projects.lightningwarning.lightning.ualf.UalfConverter
import com.projects.lightningwarning.lightning.ualf.UalfData
import org.springframework.stereotype.Component

@Component
class FrostApiService(
    private val frostRestTemplateComponent: FrostRestTemplateComponent,
    private val ualfConverter: UalfConverter,
) {

    fun getUalfLightningData(referenceTime: String, maxAge: String): List<UalfData> {
        val stringifiedUalfRows = getLightningData(referenceTime, maxAge)
        return ualfConverter.fromString(stringifiedUalfRows)
    }

    private fun getLightningData(referenceTime: String, maxAge: String): String = frostRestTemplateComponent
        .frostRestTemplate()
        .getForObject(
            "/lightning/v0.ualf?referencetime=${referenceTime}&maxage=${maxAge}",
            String::class.java
        ) ?: ""
}