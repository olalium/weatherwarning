package com.projects.lightningwarning.lightning.frostapi

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class FrostRestTemplateComponent(private val frostApiConfig: FrostApiConfig) {

    @Bean("frostRestTemplate")
    fun frostRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .rootUri("https://frost.met.no")
            .basicAuthentication(frostApiConfig.id, "")
            .build()
    }
}