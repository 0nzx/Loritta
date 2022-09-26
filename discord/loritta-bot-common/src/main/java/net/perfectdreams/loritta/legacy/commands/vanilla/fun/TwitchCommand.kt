package net.perfectdreams.loritta.legacy.commands.vanilla.`fun`

import net.perfectdreams.loritta.legacy.commands.AbstractCommand
import net.perfectdreams.loritta.legacy.commands.CommandContext
import net.perfectdreams.loritta.legacy.utils.Constants
import net.perfectdreams.loritta.legacy.common.locale.BaseLocale
import net.perfectdreams.loritta.legacy.common.locale.LocaleKeyData
import net.perfectdreams.loritta.legacy.utils.loritta
import net.dv8tion.jda.api.EmbedBuilder
import net.perfectdreams.loritta.legacy.common.commands.CommandCategory
import net.perfectdreams.loritta.legacy.api.messages.LorittaReply
import java.awt.Color

class TwitchCommand : AbstractCommand("twitch", category = CommandCategory.FUN) {
	override fun getDescriptionKey() = LocaleKeyData("commands.command.twitch.description")
	override fun getExamplesKey()  = LocaleKeyData("commands.command.twitch.examples")

	override fun canUseInPrivateChannel(): Boolean {
		return false
	}

	override suspend fun run(context: CommandContext,locale: BaseLocale) {
		if (context.args.isNotEmpty()) {
			val query = context.args.joinToString(" ")

			if (!Constants.TWITCH_USERNAME_PATTERN.matcher(query).matches()) {
				context.reply(
                        LorittaReply(
                                context.locale["commands.command.twitch.couldntFind", query],
                                Constants.ERROR
                        )
				)
				return
			}

			val payload = loritta.twitch.getUserLogin(query)

			if (payload == null) {
				context.reply(
                        LorittaReply(
								context.locale["commands.command.twitch.couldntFind", query],
                                Constants.ERROR
                        )
				)
				return
			}

			val channelName = payload.displayName
			val isPartner = payload.broadcasterType == "partner"
			val description = payload.description
			val avatarUrl = payload.profileImageUrl
			val offlineImageUrl = payload.offlineImageUrl
			val viewCount = payload.viewCount

			val embed = EmbedBuilder().apply {
				setColor(Color(101, 68, 154))
				setTitle("<:twitch:314349922755411970> $channelName")
				setDescription(description)
				if (avatarUrl.isNotEmpty()) {
					setThumbnail(avatarUrl)
				}
				if (offlineImageUrl.isNotEmpty()) {
					setImage(offlineImageUrl)
				}
				addField("\uD83D\uDCFA ${context.locale["commands.command.twitch.views"]}", viewCount.toString(), true)
			}

			context.sendMessage(context.getAsMention(true), embed.build())
		} else {
			context.explain()
		}
	}
}