package com.projects.lightningwarning.polling.lightningqueue

import com.projects.lightningwarning.sse.SSELightningObservation
import org.springframework.stereotype.Component
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Component
class LightningQueue {

    private val lightningStrikeQueue: BlockingQueue<SSELightningObservation> = LinkedBlockingQueue()

    fun addLightningObservationToQueue(lightningObservation: SSELightningObservation) =
            lightningStrikeQueue.put(lightningObservation)

    fun notEmpty(): Boolean = !lightningStrikeQueue.isEmpty()

    fun getAndRemoveItemFromQueue(): SSELightningObservation = lightningStrikeQueue.take()

    fun getSize(): Int = lightningStrikeQueue.size
}