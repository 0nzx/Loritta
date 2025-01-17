package net.perfectdreams.loritta.morenitta.website.routes.api.v1.loritta

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import net.perfectdreams.loritta.morenitta.utils.Constants
import net.perfectdreams.loritta.morenitta.utils.extensions.getOrNull
import io.ktor.server.application.ApplicationCall
import net.perfectdreams.loritta.morenitta.LorittaBot
import net.perfectdreams.loritta.morenitta.utils.config.FanArtArtist
import net.perfectdreams.loritta.morenitta.utils.extensions.objectNode
import net.perfectdreams.loritta.morenitta.utils.extensions.set
import net.perfectdreams.sequins.ktor.BaseRoute
import net.perfectdreams.loritta.morenitta.website.utils.extensions.respondJson

class GetFanArtsController(val loritta: LorittaBot) : BaseRoute("/api/v1/loritta/fan-arts") {
	override suspend fun onRequest(call: ApplicationCall) {
		loritta as LorittaBot

		val query = call.parameters["query"]
		val filter = call.parameters["filter"]?.split(",")

		val fanArtArtists = loritta.fanArtArtists
				.let {
					if (filter != null)
						it.filter { it.id in filter }
					else
						it
				}

		val fanArtists = Constants.JSON_MAPPER.valueToTree<JsonNode>(fanArtArtists)

		if (query == "all") {
			val discordIds = fanArtArtists
					.mapNotNull {
						it.socialNetworks?.asSequence()?.filterIsInstance<FanArtArtist.SocialNetwork.DiscordSocialNetwork>()
								?.firstOrNull()?.let { discordInfo ->
									discordInfo.id.toLong()
								}
					}

			val userProfiles = discordIds.map { it to loritta.getLorittaProfileDeferred(it) }
			val users = discordIds.asSequence().mapNotNull { loritta.cachedRetrievedArtists.getIfPresent(it)?.getOrNull() }

			fanArtists.forEach {
				val discordInfo = it["networks"]?.firstOrNull { it["type"].textValue() == "discord" }

				if (discordInfo != null) {
					val id = discordInfo["id"].textValue()
					val user = users.firstOrNull { it.id.toString() == id }
					val profileJob = userProfiles.firstOrNull { entry -> entry.first == id.toLong() }

					if (profileJob != null) {
						val profile = profileJob.second.await()
						if (profile != null) {
							val aboutMe = loritta.newSuspendedTransaction {
								profile.settings.aboutMe
							}
							if (aboutMe != null) {
								it as ObjectNode
								it.set("aboutMe", aboutMe)
							}
						}
					}

					if (user != null) {
						it as ObjectNode
						it.set<JsonNode>("user", objectNode(
								"id" to user.id,
								"name" to user.name,
								"effectiveAvatarUrl" to user.effectiveAvatarUrl
						))
					}
				}
			}
		}

		call.respondJson(fanArtists)
	}
}