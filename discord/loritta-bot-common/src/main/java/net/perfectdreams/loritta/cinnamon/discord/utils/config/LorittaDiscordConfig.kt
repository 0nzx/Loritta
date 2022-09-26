package net.perfectdreams.loritta.cinnamon.discord.utils.config

import kotlinx.serialization.Serializable

@Serializable
class LorittaDiscordConfig(
    val token: String,
    val applicationId: Long
)