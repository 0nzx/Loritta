package net.perfectdreams.loritta.cinnamon.discord.utils

import net.perfectdreams.loritta.cinnamon.discord.LorittaCinnamon
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CinnamonTasks(m: LorittaCinnamon) {
    private val executorService = Executors.newScheduledThreadPool(2)
    private val eventAnalytics = EventAnalyticsTask(m)

    fun start() {
        // Every 5s
        executorService.scheduleAtFixedRate(eventAnalytics, 0L, 5L, TimeUnit.SECONDS)
    }

    fun shutdown() {
        executorService.shutdown()
    }
}