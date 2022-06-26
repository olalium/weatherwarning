package com.projects.lightningwarning.sse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import com.projects.lightningwarning.polling.lightningqueue.LightningQueue
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.logging.Logger
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
    private val mapper = Gson()
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @PostConstruct
    private fun connectToSse() {
        val type = object : ParameterizedTypeReference<ServerSentEvent<String>>() {}
        logger.info("subscribing to event stream...")
        val webClient = WebClient.create()
        val eventStream = webClient.get()
            .uri("https://lyn.met.no/events")
            .retrieve()
            .bodyToFlux(type)

        eventStream.subscribe ({ onMessageReceived(it) }, {onError(it)})
    }

    private fun onMessageReceived(message: ServerSentEvent<String>) {
        val data = message.data()
        if (message.event() == "lx" && !data.isNullOrBlank()) {
            addServerSentEventDataToQueue(data)
        }
    }

    private fun addServerSentEventDataToQueue(stringifiedObservations: String) {
        val stringifiedObservation = stringifiedObservations.split("\n")
        stringifiedObservation.forEach {
            val lightningObservation = mapper.fromJson(it, Array<SSELightningObservation>::class.java).first()
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