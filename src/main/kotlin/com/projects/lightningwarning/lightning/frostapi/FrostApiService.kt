package com.projects.lightningwarning.lightning.frostapi

import org.springframework.stereotype.Component

@Component
class FrostApiService(private val frostRestTemplateComponent: FrostRestTemplateComponent) {

    fun getLightningData (referenceTime: String, maxAge: String): String = frostRestTemplateComponent
        .frostRestTemplate()
        .getForObject(
            "/lightning/v0.ualf?referencetime=${referenceTime}&maxage=${maxAge}",
            String::class.java
        ) ?: ""
}