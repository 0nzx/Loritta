package net.perfectdreams.loritta.cinnamon.pudding.tables.cache

import net.perfectdreams.exposedpowerutils.sql.javatime.timestampWithTimeZone
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object DiscordGuilds : IdTable<Long>() {
    override val id: Column<EntityID<Long>> = long("id").entityId()
    override val primaryKey = PrimaryKey(id)

    val guildId = long("guild").index()
    val name = text("name")
    val icon = text("icon").nullable()
    val ownerId = long("owner")
    val joinedAt = timestampWithTimeZone("joined_at")
}
