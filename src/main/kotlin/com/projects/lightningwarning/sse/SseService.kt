package com.projects.lightningwarning.sse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import com.projects.lightningwarning.polling.lightningqueue.LightningQueue
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
    private val mapper = Gson()

    @PostConstruct
    private fun connectToSse() {
        val webClient = WebClient.create()
        val type = object : ParameterizedTypeReference<ServerSentEvent<String>>() {}
        val eventStream = webClient.get()
                .uri("https://lyn.met.no/events")
                .retrieve()
                .bodyToFlux(type)

        println("subscribing on event stream...")
        eventStream.subscribe(
                { onMessageReceived(it) },
                { println("error occurred $it") },
                { println("Completed!!!") })
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
            lightningQueue.addLightningObservationToQueue(lightningObservation)
        }
    }
}