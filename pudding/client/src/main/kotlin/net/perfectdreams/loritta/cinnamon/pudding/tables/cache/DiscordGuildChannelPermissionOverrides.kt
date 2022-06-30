package net.perfectdreams.loritta.cinnamon.pudding.tables.cache

import org.jetbrains.exposed.dao.id.LongIdTable

object DiscordGuildChannelPermissionOverrides : LongIdTable() {
    val guildId = long("guild").index()
    val channelId = long("channel").index()
    val entityId = long("entity_id").index()
    val type = integer("type")
    val allow = long("allow")
    val deny = long("deny")

    init {
        index(true, guildId, channelId, entityId)
    }
}
