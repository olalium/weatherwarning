package com.projects.lightningwarning.sse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.projects.lightningwarning.polling.lightningqueue.LightningQueue
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import javax.annotation.PostConstruct

@JsonIgnoreProperties
data class SSELightningObservation (
    val Epoch: String,
    val Point: List<Double>,
    val CloudIndicator: Int
)

@Component
class SseService(
    private val lightningQueue: LightningQueue
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @PostConstruct
    private fun connectToSse() {
        val type = object : ParameterizedTypeReference<ServerSentEvent<List<List<Double>>>>() {}
        logger.info("subscribing to event stream...")
        val webClient = WebClient.create()
        val eventStream = webClient.get()
            .uri("https://lyn.met.no/events")
            .retrieve()
            .bodyToFlux(type)

        eventStream.subscribe ({ onMessageReceived(it) }, {onError(it)})
    }

    private fun onMessageReceived(message: ServerSentEvent<List<List<Double>>>) {
        val data = message.data()
        if (message.event() == "lx" && !data.isNullOrEmpty()) {
            addServerSentEventDataToQueue(data)
        }
    }

    private fun addServerSentEventDataToQueue(observations: List<List<Double>>) {
        observations.forEach {
            val lightningObservation = SSELightningObservation(it[0].toString(), listOf( it[1], it[2]), it[3].toInt())
            logger.info("lightning observed at ${lightningObservation.Epoch}, adding to queue")
            lightningQueue.addLightningObservationToQueue(lightningObservation)
        }
    }

    private fun onError(exception: Throwable) {
        logger.warn("Exception while listening to event stream: ${exception}")
        logger.info("retrying to subscribe to event stream...")
        connectToSse()
    }
}