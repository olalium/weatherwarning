package com.projects.lightningwarning

import com.projects.lightningwarning.polling.PollingServiceConfig
import com.projects.lightningwarning.lightning.LocationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(PollingServiceConfig::class, LocationConfig::class)
class LightningwarningApplication

fun main(args: Array<String>) {
	runApplication<LightningwarningApplication>(*args)
}
