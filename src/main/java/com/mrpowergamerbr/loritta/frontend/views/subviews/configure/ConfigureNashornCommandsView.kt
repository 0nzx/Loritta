package com.mrpowergamerbr.loritta.frontend.views.subviews.configure

import com.google.gson.JsonArray
import com.mrpowergamerbr.loritta.Loritta
import com.mrpowergamerbr.loritta.frontend.evaluate
import com.mrpowergamerbr.loritta.userdata.ServerConfig
import com.mrpowergamerbr.loritta.utils.oauth2.TemmieDiscordAuth
import net.dv8tion.jda.core.entities.Guild
import org.jooby.Request
import org.jooby.Response

class ConfigureNashornCommandsView : ConfigureView() {
	override fun handleRender(req: Request, res: Response, variables: MutableMap<String, Any?>): Boolean {
		super.handleRender(req, res, variables)
		return req.path().matches(Regex("^/dashboard/configure/[0-9]+/nashorn"))
	}

	override fun renderConfiguration(req: Request, res: Response, variables: MutableMap<String, Any?>, discordAuth: TemmieDiscordAuth, guild: Guild, serverConfig: ServerConfig): String {
		variables["saveType"] = "nashorn_commands"

		val feeds = JsonArray()
		serverConfig.nashornCommands.forEach {
			val json = Loritta.gson.toJsonTree(it)
			feeds.add(json)
		}

		variables["commands"] = feeds.toString()

		return evaluate("configure_nashorn.html", variables)
	}
}