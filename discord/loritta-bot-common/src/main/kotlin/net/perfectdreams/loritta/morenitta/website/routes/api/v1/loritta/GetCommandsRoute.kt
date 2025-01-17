package net.perfectdreams.loritta.morenitta.website.routes.api.v1.loritta

import io.ktor.server.application.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.morenitta.platform.discord.legacy.commands.DiscordCommand
import net.perfectdreams.loritta.serializable.CommandInfo
import net.perfectdreams.loritta.morenitta.website.utils.extensions.respondJson
import net.perfectdreams.sequins.ktor.BaseRoute

class GetCommandsRoute(val loritta: LorittaBot) : BaseRoute("/api/v1/loritta/commands/{localeId}") {
	override suspend fun onRequest(call: ApplicationCall) {
		val localeId = call.parameters["localeId"] ?: return

		val locale = loritta.localeManager.getLocaleById(localeId)

		val commands = loritta.legacyCommandManager.commandMap.map {
			CommandInfo(
					it::class.java.simpleName,
					it.label,
					it.aliases,
					it.category,
					it.getDescriptionKey(),
					it.getUsage(),
					it.getExamplesKey(),
					it.cooldown,
					it.canUseInPrivateChannel(),
					it.getDiscordPermissions().map { it.name },
					it.lorittaPermissions.map { it.name },
					it.getBotPermissions().map { it.name },
					listOf() // Old API doesn't has SimilarCommands
			)
		} + loritta.commandMap.commands.filter { !it.hideInHelp }.map {
			var botRequiredPermissions = listOf<String>()
			var userRequiredPermissions = listOf<String>()
			var userRequiredLorittaPermissions = listOf<String>()

			if (it is DiscordCommand) {
				botRequiredPermissions = it.botRequiredPermissions.map { it.name }
				userRequiredPermissions = it.userRequiredPermissions.map { it.name }
				userRequiredLorittaPermissions = it.userRequiredLorittaPermissions.map { it.name }
			}

			CommandInfo(
					it.commandName,
					it.labels.first(),
					it.labels.drop(1).toList(),
					it.category,
					it.descriptionKey,
					it.usage,
					it.examplesKey,
					it.cooldown,
					it.canUseInPrivateChannel,
					userRequiredPermissions,
					userRequiredLorittaPermissions,
					botRequiredPermissions,
					it.similarCommands
			)
		}

		call.respondJson(Json.encodeToString(ListSerializer(CommandInfo.serializer()), commands))
	}
}