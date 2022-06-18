package com.projects.lightningwarning.lightning.frostapi

import com.projects.lightningwarning.lightning.RestTemplateConfig
import org.springframework.stereotype.Component

@Component
class FrostApiService(val restTemplateConfig: RestTemplateConfig) {

    fun getLightningData (referenceTime: String, maxAge: String): String = restTemplateConfig
        .frostRestTemplate()
        .getForObject(
            "/lightning/v0.ualf?referencetime=${referenceTime}&maxage=${maxAge}",
            String::class.java
        ) ?: ""
}