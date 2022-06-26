package com.projects.lightningwarning.polling.lightningqueue

import com.projects.lightningwarning.sse.SSELightningObservation
import org.springframework.stereotype.Component
import java.util.LinkedList
import java.util.Queue

@Component
class LightningQueue {

    private val lightningStrikeQueue: Queue<SSELightningObservation> = LinkedList()

    fun addLightningObservationToQueue(lightningObservation: SSELightningObservation) =
            lightningStrikeQueue.add(lightningObservation)

    fun notEmpty(): Boolean = !lightningStrikeQueue.isEmpty()

    fun getAndRemoveItemFromQueue(): SSELightningObservation = lightningStrikeQueue.poll()

    fun getSize(): Int = lightningStrikeQueue.size
}