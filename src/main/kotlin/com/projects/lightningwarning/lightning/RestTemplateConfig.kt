package com.projects.lightningwarning.lightning

import com.projects.lightningwarning.lightning.frostapi.FrostApiConfig
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RestTemplateConfig(val frostApiConfig: FrostApiConfig) {

    @Bean("frostRestTemplate")
    fun frostRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .rootUri("https://frost.met.no")
            .basicAuthentication(frostApiConfig.id, "")
            .build()
    }
}