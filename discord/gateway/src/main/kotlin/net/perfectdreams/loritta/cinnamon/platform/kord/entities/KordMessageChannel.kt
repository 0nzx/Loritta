package net.perfectdreams.loritta.cinnamon.platform.kord.entities

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createMessage
import dev.kord.rest.builder.message.EmbedBuilder
import net.perfectdreams.loritta.cinnamon.common.entities.LorittaEmbed
import net.perfectdreams.loritta.cinnamon.common.entities.LorittaMessage
import net.perfectdreams.loritta.cinnamon.discord.objects.LorittaDiscordMessageChannel

class KordMessageChannel(private val handle: dev.kord.core.entity.channel.MessageChannel) : LorittaDiscordMessageChannel {
    /* override val id: Long = handle.id.value
    override val name: String? = handle.data.name.value
    override val topic: String? = handle.data.topic.value
    override val nsfw: Boolean? = handle.data.nsfw.value
    override val guildId: Long? = handle.data.guildId.value?.value
    override val creation: Instant = handle.id.timeStamp */

    override suspend fun sendMessage(message: LorittaMessage) {
        val embed = message.embed
        handle.createMessage {
            content = buildString {
                if (message.content != null)
                    append(message.content)

                for (reply in message.replies) {
                    append("\n")
                    append("${reply.prefix} **|** ${reply.content}")
                }
            }

            if (embed != null) {
                embed {
                    title = embed.title
                    description = embed.description
                    image = embed.image
                    thumbnail = embed.thumbnail?.let { url -> EmbedBuilder.Thumbnail().also {it.url = url}}
                    color = embed.color?.let { Color(it.rgb) }
                    timestamp = embed.timestamp
                    author = embed.author?.toKord()
                    fields = embed.fields.map { it.toKord() }.toMutableList()
                    footer = embed.footer?.toKord()
                }
            }
        }
    }

    private fun LorittaEmbed.Author.toKord() = EmbedBuilder.Author().also {
        it.name = name
        it.icon = icon
        it.url = url
    }

    private fun LorittaEmbed.Field.toKord() = EmbedBuilder.Field().also {
        it.name = name
        it.value = value
        it.inline = inline
    }

    private fun LorittaEmbed.Footer.toKord() = EmbedBuilder.Footer().also {
        it.text = text
        it.icon = icon
    }
}