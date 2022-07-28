package net.perfectdreams.loritta.cinnamon.discord.gateway.utils.config

import kotlinx.serialization.Serializable
import net.perfectdreams.loritta.cinnamon.utils.config.LorittaConfig
import net.perfectdreams.loritta.cinnamon.discord.utils.config.DiscordInteractionsConfig
import net.perfectdreams.loritta.cinnamon.discord.utils.config.LorittaDiscordConfig
import net.perfectdreams.loritta.cinnamon.discord.utils.config.ServicesConfig

@Serializable
data class RootConfig(
    val loritta: LorittaConfig,
    val discord: LorittaDiscordConfig,
    val interactions: DiscordInteractionsConfig,
    val services: ServicesConfig
)