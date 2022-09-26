package net.perfectdreams.loritta.morenitta.website.routes.api.v1.guild

import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.set
import net.perfectdreams.loritta.morenitta.dao.ServerConfig
import io.ktor.server.application.ApplicationCall
import net.dv8tion.jda.api.entities.Guild
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.morenitta.website.routes.api.v1.RequiresAPIGuildAuthRoute
import net.perfectdreams.loritta.morenitta.website.session.LorittaJsonWebSession
import net.perfectdreams.loritta.morenitta.website.utils.config.types.ConfigTransformers
import net.perfectdreams.loritta.morenitta.website.utils.extensions.respondJson
import net.perfectdreams.temmiediscordauth.TemmieDiscordAuth

class GetServerConfigSectionRoute(loritta: LorittaBot) : RequiresAPIGuildAuthRoute(loritta, "/config/{sections}") {
	override suspend fun onGuildAuthenticatedRequest(call: ApplicationCall, discordAuth: TemmieDiscordAuth, userIdentification: LorittaJsonWebSession.UserIdentification, guild: Guild, serverConfig: ServerConfig) {
		val sections = call.parameters["sections"]!!.split(",").toSet()

		val payload = jsonObject()

		for (section in sections) {
			val transformer = ConfigTransformers.ALL_TRANSFORMERS.firstOrNull { it.payloadType == section }

			if (transformer != null)
				payload[transformer.configKey] = transformer.toJson(userIdentification, guild, serverConfig)
		}

		call.respondJson(payload)
	}
}