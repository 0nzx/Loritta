package net.perfectdreams.loritta.morenitta.website.views.subviews.api.config.types

import com.google.gson.JsonObject
import net.perfectdreams.loritta.morenitta.dao.ServerConfig
import net.perfectdreams.loritta.deviousfun.entities.Guild
import net.perfectdreams.loritta.morenitta.website.session.LorittaJsonWebSession

abstract class ConfigPayloadType(val type: String) {
    abstract fun process(
        payload: JsonObject,
        userIdentification: LorittaJsonWebSession.UserIdentification,
        serverConfig: ServerConfig,
        guild: Guild
    )
}