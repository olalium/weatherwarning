package com.projects.lightningwarning.lightning

import com.projects.lightningwarning.polling.PollingServiceConfig
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RestTemplateConfig(val pollingServiceConfig: PollingServiceConfig) {

    @Bean("frostRestTemplate")
    fun frostRestTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .rootUri("https://frost.met.no")
            .basicAuthentication(pollingServiceConfig.id, "")
            .build()
    }
}