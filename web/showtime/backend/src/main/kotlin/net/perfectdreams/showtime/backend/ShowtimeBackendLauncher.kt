package net.perfectdreams.showtime.backend

import mu.KotlinLogging
import net.perfectdreams.loritta.cinnamon.common.locale.LanguageManager
import net.perfectdreams.loritta.cinnamon.common.utils.config.ConfigUtils
import net.perfectdreams.showtime.backend.utils.config.RootConfig

object ShowtimeBackendLauncher {
    private val logger = KotlinLogging.logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        val rootConfig = ConfigUtils.loadAndParseConfigOrCopyFromJarAndExit<RootConfig>(ShowtimeBackendLauncher::class, System.getProperty("showtime.config", "showtime.conf"))
        logger.info { "Loaded Showtime Backend's configuration file" }

        val languageManager = LanguageManager(
            ShowtimeBackendLauncher::class,
            "en",
            "/languages/"
        )
        languageManager.loadLanguagesAndContexts()

        val showtime = ShowtimeBackend(rootConfig, languageManager)
        showtime.start()
    }
}