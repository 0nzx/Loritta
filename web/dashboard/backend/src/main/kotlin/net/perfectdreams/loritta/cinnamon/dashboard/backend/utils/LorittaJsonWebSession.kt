package net.perfectdreams.loritta.cinnamon.dashboard.backend.utils

import dev.kord.common.entity.DiscordUser
import io.ktor.server.application.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging

data class LorittaJsonWebSession(
    val base64CachedIdentification: String?,
    val base64StoredDiscordAuthTokens: String?
) {
    companion object {
        fun empty() = LorittaJsonWebSession(
            null,
            null
        )

        private val logger = KotlinLogging.logger {}
    }

    @Serializable
    data class UserIdentification(
        val id: String,
        val username: String,
        val discriminator: String,
        val verified: Boolean,
        val email: String? = null, // Looks like this can be missing
        val avatar: String? = null, // Looks like this can be missing
        val createdAt: Long,
        val updatedAt: Long
    )

    @Serializable
    data class StoredDiscordAuthTokens(
        val authCode: String? = null,
        val redirectUri: String,
        val scope: List<String>,
        val accessToken: String? = null,
        val refreshToken: String? = null,
        val expiresIn: Long? = null,
        val generatedAt: Long? = null
    )
}