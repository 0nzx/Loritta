package net.perfectdreams.loritta.platform.interaktions.entities

import net.perfectdreams.discordinteraktions.api.entities.Snowflake
import net.perfectdreams.discordinteraktions.common.context.commands.SlashCommandContext
import net.perfectdreams.discordinteraktions.common.utils.AllowedMentions
import net.perfectdreams.loritta.common.entities.LorittaMessage

class InteraKTionsMessageChannelHandler(private val context: SlashCommandContext) : StaticInteraKTionsMessageChannel() {
    override suspend fun sendMessage(message: LorittaMessage) {
        context.sendMessage {
            val impersonation = message.impersonation

            // TODO: Fix
            /* if (impersonation != null) {
                // We are going to *impersonate* someone (woo)
                // Because we can't use webhooks, we will replace them with a embed
                embed {
                    author {
                        name = impersonation.username
                        icon = impersonation.avatar.url
                    }

                    body {
                        description = message.content
                    }
                }
            } else { */
                content = buildString {
                    if (message.content != null)
                        append(message.content)

                    for (reply in message.replies) {
                        append("\n")
                        append("${reply.prefix} **|** ${reply.content}")
                    }
                }
            // }

            for (file in message.files) {
                addFile(file.key, file.value.inputStream())
            }

            /* val embed = message.embed
            if (embed != null) {
                embed {
                    author {
                        name = embed.author?.name
                        icon = embed.author?.icon
                        url = embed.author?.url
                    }
                    body {
                        title = embed.title
                        description = embed.description
                        color = java.awt.Color(embed.color?.rgb ?: 0)
                    }
                    embed.fields.forEach {
                        field(it.name, it.value) {
                            inline = it.inline
                        }
                    }
                    images {
                        image = embed.image
                        thumbnail = embed.thumbnail
                    }
                    footer(embed.footer?.text ?: return@embed) {
                        icon = embed.footer?.icon
                    }
                }
            } */

            // Keep in mind that ephemeral messages do not support *everything*, so let's throw a exception if
            // we are using stuff that Discord ephemeral messages do not support
            if (message.isEphemeral) {
                if (message.embed != null)
                    throw UnsupportedOperationException("Ephemeral Messages do not support embeds!")
                if (message.files.isNotEmpty())
                    throw UnsupportedOperationException("Ephemeral Messages do not support files!")

                isEphemeral = true
            }

            // Allowed Mentions
            // By default we will disable ALL mentions, to avoid a "@everyone moment 3.0"
            // Messages can enable specific mentions if needed
            allowedMentions = AllowedMentions(
                users = message.allowedMentions.users.map { Snowflake(it.id) },
                roles = listOf(), // TODO: Add support
                repliedUser = message.allowedMentions.repliedUser
            )
        }
    }
}